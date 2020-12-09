package de.fornalik.webclient.petrolstation.pricing;

import de.fornalik.webclient.application.webclient.WebClientSupport;
import de.fornalik.webclient.location.address.Geo;
import de.fornalik.webclient.petrolstation.data.PetrolStation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public final class PetrolStationWebClient {
  @NonNull private final WebClient webClient;
  @NonNull private final PetrolStationNeighbourhoodRequest request;
  @NonNull private final Converter<String, Flux<PetrolStation>> jsonConverter;

  @Autowired
  public PetrolStationWebClient(
      WebClient.Builder clientBuilder,
      PetrolStationNeighbourhoodRequest request,
      Converter<String, Flux<PetrolStation>> petrolStationsNeighbourhoodResponseMapper) {

    this.webClient = createWebClient(clientBuilder);
    this.request = request;
    this.jsonConverter = petrolStationsNeighbourhoodResponseMapper;
  }

  @NonNull
  private WebClient createWebClient(WebClient.Builder clientBuilder) {
    return clientBuilder
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.USER_AGENT, "I'm a testing teapot")
        .build();
  }

  @NonNull
  public Flux<PetrolStation> getPetrolStationsInNeighbourhood(Geo geo) {
    request.setGeoLocation(geo.getLatitude(), geo.getLongitude());
    request.setDistance(geo.getDistance());

    return webClient.get()
        .uri(request.getUri())
        .exchangeToMono(WebClientSupport::processRawResponse)
        .flux()
        .flatMap(jsonConverter::convert);
  }
}
