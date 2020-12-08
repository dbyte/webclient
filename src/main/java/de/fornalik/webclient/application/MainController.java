package de.fornalik.webclient.application;

import de.fornalik.webclient.SpringWebclientApplication;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import de.fornalik.webclient.business.PetrolStation;
import de.fornalik.webclient.service.GeocodingClientService;
import de.fornalik.webclient.service.PetrolStationClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.SignalType;

import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

  private final PetrolStationClientService petrolStationClientService;
  private final GeocodingClientService geocodingClientService;

  public void doRequestPetrolStations(Geo geo) {
    petrolStationClientService
        .getPetrolStationsInNeighbourhood(geo)
        .doFinally(this::onCompleted)
        .subscribe(
            this::onReceivedPetrolStation,
            Throwable::printStackTrace);

    log.debug("************* ASYNC PS *************");
  }

  public void doRequestGeoLocation(Address address) {
    geocodingClientService
        .getGeoLocationForAddress(address)
        .doFinally(this::onCompleted)
        .subscribe(
            this::onReceivedGeoLocation,
            Throwable::printStackTrace);

    log.debug("************* ASYNC GEO *************");
  }

  private void onReceivedPetrolStation(PetrolStation petrolStation) {
    log.debug("************* RESULT PS *************");
    log.debug(petrolStation.toString());
  }

  private void onReceivedGeoLocation(Geo geo) {
    log.debug("************* RESULT GEO *************");
    log.debug(geo.toString());
  }

  private void onCompleted(SignalType signalType) {
    log.debug("************* COMPLETED with signal: {} *************", signalType);
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
