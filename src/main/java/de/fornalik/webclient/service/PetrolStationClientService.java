package de.fornalik.webclient.service;

import de.fornalik.webclient.business.Geo;
import de.fornalik.webclient.business.PetrolStation;
import de.fornalik.webclient.webclient.PetrolStationNeighbourhoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class PetrolStationClientService {
  private final WebClient webClient;
  private final PetrolStationNeighbourhoodRequest request;
  private final Converter<String, Flux<PetrolStation>> jsonConverter;

  @Autowired
  public PetrolStationClientService(
      WebClient.Builder clientBuilder,
      PetrolStationNeighbourhoodRequest request,
      Converter<String, Flux<PetrolStation>> petrolStationsNeighbourhoodResponseMapper) {

    this.webClient = createWebClient(clientBuilder);
    this.request = request;
    this.jsonConverter = petrolStationsNeighbourhoodResponseMapper;
  }

  private WebClient createWebClient(WebClient.Builder clientBuilder) {
    return clientBuilder
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader(HttpHeaders.USER_AGENT, "I'm a testing teapot")
        .build();
  }

  public Flux<PetrolStation> getPetrolStationsInNeighbourhood(Geo geo) {
    request.setGeoLocation(geo.getLatitude(), geo.getLongitude());
    request.setDistance(geo.getDistance());

    return webClient.get()
        .uri(request.getUri())
        .exchangeToMono(ClientServiceSupport::processRawResponse)
        .flux()
        .flatMap(jsonConverter::convert);
  }
}
