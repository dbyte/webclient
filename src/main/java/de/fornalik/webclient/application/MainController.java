package de.fornalik.webclient.application;

import de.fornalik.webclient.SpringWebclientApplication;
import de.fornalik.webclient.location.address.Address;
import de.fornalik.webclient.location.address.Geo;
import de.fornalik.webclient.location.geocoding.GeocodingWebClient;
import de.fornalik.webclient.messaging.common.MessageContent;
import de.fornalik.webclient.messaging.pushover.PushoverWebClient;
import de.fornalik.webclient.petrolstation.data.PetrolStation;
import de.fornalik.webclient.petrolstation.pricing.PetrolStationWebClient;
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
public final class MainController {

  private final PetrolStationWebClient petrolStationWebClient;
  private final GeocodingWebClient geocodingWebClient;
  private final PushoverWebClient pushoverWebClient;

  public Flux<PetrolStation> findPetrolStations(@NonNull Geo geo) {
    return petrolStationWebClient
        .getPetrolStationsInNeighbourhood(geo)
        .doOnNext(petrolStation -> log.debug(petrolStation.toString()))
        .doFinally(signal -> log
            .debug("************* Completed STATIONS signal: {} *************", signal));
  }

  public Mono<Geo> findGeoLocation(@NonNull Address address) {
    return geocodingWebClient
        .getGeoLocationForAddress(address)
        .doOnNext(geo -> log.debug(geo.toString()))
        .doFinally(signal -> log
            .debug("************* Completed GEO signal: {} *************", signal));
  }

  public Mono<Void> sendPushoverMessage(@NonNull MessageContent content) {
    return pushoverWebClient
        .sendMessage(content)
        .doFinally(signal -> log
            .debug("************* Completed PUSHOVER signal: {} *************", signal));
  }

  private String readJsonTest() {
    // The class loader that loaded the class
    ClassLoader classLoader = SpringWebclientApplication.class.getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("tankerkoenig_ok_response.json");

    // The stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found!");
    }

    java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }
}
