package de.fornalik.webclient.messaging.pushover;

import de.fornalik.webclient.application.webclient.WebClientSupport;
import de.fornalik.webclient.messaging.common.MessageContent;
import de.fornalik.webclient.messaging.common.MessageRequest;
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
public final class PushoverWebClient {
  @NonNull private final WebClient webClient;
  @NonNull private final MessageRequest request;
  @NonNull private final Converter<String, Mono<Void>> jsonConverter;

  @Autowired
  public PushoverWebClient(
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
        .exchangeToMono(WebClientSupport::processRawResponse)
        .flatMap(jsonConverter::convert);
  }
}
