package de.fornalik.webclient.location.geocoding;

import de.fornalik.webclient.application.webclient.WebClientSupport;
import de.fornalik.webclient.location.address.Address;
import de.fornalik.webclient.location.address.Geo;
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
public final class GeocodingWebClient {
  @NonNull private final WebClient webClient;
  @NonNull private final AddressRequest request;
  @NonNull private final Converter<String, Mono<Geo>> jsonConverter;

  @Autowired
  public GeocodingWebClient(
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
        .exchangeToMono(WebClientSupport::processRawResponse)
        .flatMap(jsonConverter::convert);
  }
}
