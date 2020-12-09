package de.fornalik.webclient.service;

import de.fornalik.webclient.webclient.MessageContent;
import de.fornalik.webclient.webclient.MessageRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
public class PushoverMessageClientService {
  private final WebClient webClient;
  private final MessageRequest request;
  private final Converter<String, Mono<Void>> jsonConverter;

  @Autowired
  public PushoverMessageClientService(
      WebClient.Builder clientBuilder,
      MessageRequest request,
      Converter<String, Mono<Void>> pushoverMessageResponseMapper) {

    this.webClient = createWebClient(clientBuilder);
    this.request = request;
    this.jsonConverter = pushoverMessageResponseMapper;
  }

  @NonNull
  private WebClient createWebClient(WebClient.Builder clientBuilder) {
    return clientBuilder
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name())
        .defaultHeader(HttpHeaders.USER_AGENT, "I'm a testing teapot")
        .build();
  }

  @NonNull
  public Mono<Void> sendMessage(MessageContent messageContent) {
    request.setMessage(messageContent);
    // System.out.println(request.getBody());

    return webClient.post()
        .uri(request.getUri())
        .body(BodyInserters.fromValue(request.getBody()))
        .exchangeToMono(ClientServiceSupport::processRawResponse)
        .flatMap(jsonConverter::convert);
  }
}
