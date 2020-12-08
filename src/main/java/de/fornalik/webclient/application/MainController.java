package de.fornalik.webclient.application;

import de.fornalik.webclient.SpringWebclientApplication;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import de.fornalik.webclient.business.PetrolStation;
import de.fornalik.webclient.service.GeocodingClientService;
import de.fornalik.webclient.service.PetrolStationClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

  private final PetrolStationClientService petrolStationClientService;
  private final GeocodingClientService geocodingClientService;

  public Flux<PetrolStation> findPetrolStations(@NonNull Geo geo) {
    return petrolStationClientService
        .getPetrolStationsInNeighbourhood(geo)
        .doOnNext(petrolStation -> log.debug(petrolStation.toString()))
        .doFinally(signal -> log
            .debug("************* COMPLETED STATIONS signal: {} *************", signal));
  }

  public Mono<Geo> findGeoLocation(@NonNull Address address) {
    return geocodingClientService
        .getGeoLocationForAddress(address)
        .doOnNext(geo -> log.debug(geo.toString()))
        .doFinally(signal -> log
            .debug("************* COMPLETED GEO signal: {} *************", signal));
  }

  private String readJsonTest() {
    // The class loader that loaded the class
    ClassLoader classLoader = SpringWebclientApplication.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("responseFixture.json");

    // The stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found!");
    }

    java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }
}
