package de.fornalik.webclient.application;

import de.fornalik.webclient.SpringWebclientApplication;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import de.fornalik.webclient.business.PetrolStation;
import de.fornalik.webclient.service.GeocodingClientService;
import de.fornalik.webclient.service.PetrolStationClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.SignalType;

import java.io.InputStream;

@Controller
public class MainController {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

  private final PetrolStationClientService petrolStationClientService;
  private final GeocodingClientService geocodingClientService;

  @Autowired
  public MainController(
      PetrolStationClientService petrolStationClientService,
      GeocodingClientService geocodingClientService) {

    this.petrolStationClientService = petrolStationClientService;
    this.geocodingClientService = geocodingClientService;
  }

  public void doRequestPetrolStations(Geo geo) {
    petrolStationClientService
        .getPetrolStationsInNeighbourhood(geo)
        .doFinally(this::onCompleted)
        .subscribe(
            this::onReceivedPetrolStation,
            Throwable::printStackTrace);

    LOGGER.debug("************* ASYNC PS *************");
  }

  public void doRequestGeoLocation(Address address) {
    geocodingClientService
        .getGeoLocationForAddress(address)
        .doFinally(this::onCompleted)
        .subscribe(
            this::onReceivedGeoLocation,
            Throwable::printStackTrace);

    LOGGER.debug("************* ASYNC GEO *************");
  }

  private void onReceivedPetrolStation(PetrolStation petrolStation) {
    LOGGER.debug("************* RESULT PS *************");
    LOGGER.debug(petrolStation.toString());
  }

  private void onReceivedGeoLocation(Geo geo) {
    LOGGER.debug("************* RESULT GEO *************");
    LOGGER.debug(geo.toString());
  }

  private void onCompleted(SignalType signalType) {
    LOGGER.debug("************* COMPLETED with signal: {} *************", signalType);
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
