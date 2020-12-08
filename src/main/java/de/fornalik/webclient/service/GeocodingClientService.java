package de.fornalik.webclient.service;

import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import de.fornalik.webclient.webclient.AddressRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeocodingClientService {
  private final WebClient webClient;
  private final AddressRequest request;
  private final Converter<String, Mono<Geo>> jsonConverter;

  @Autowired
  public GeocodingClientService(
      WebClient.Builder clientBuilder,
      @Qualifier("geocodingRequest") AddressRequest request,
      Converter<String, Mono<Geo>> geocodingResponseMapper) {

    this.webClient = createWebClient(clientBuilder);
    this.request = request;
    this.jsonConverter = geocodingResponseMapper;
  }

  @NonNull
  private WebClient createWebClient(WebClient.Builder clientBuilder) {
    return clientBuilder
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.USER_AGENT, "I'm a testing teapot")
        .build();
  }

  @NonNull
  public Mono<Geo> getGeoLocationForAddress(Address a) {
    request.setAddressLocation(a.getStreet(), a.getHouseNumber(), a.getCity(), a.getPostCode());

    return webClient.get()
        .uri(request.getUri())
        .exchangeToMono(ClientServiceSupport::processRawResponse)
        .flatMap(jsonConverter::convert);
  }
}
