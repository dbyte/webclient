package de.fornalik.webclient.webclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

@Component
public class TankerkoenigNeighbourhoodRequest implements PetrolStationNeighbourhoodRequest {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(TankerkoenigNeighbourhoodRequest.class);

  private final String petrolStationApiKey;
  private final UriBuilderFacade uriBuilderFacade;

  @Autowired
  public TankerkoenigNeighbourhoodRequest(
      UriBuilderFacade uriBuilderFacade,
      @Value("${app.webclient.apikey.petrolstations:}") String petrolStationApiKey) {

    this.petrolStationApiKey = petrolStationApiKey;
    this.uriBuilderFacade = uriBuilderFacade;
  }

  @PostConstruct
  private void init() {
    setDefaultParams();
  }

  private void setDefaultParams() {
    if (petrolStationApiKey.isEmpty()) {
      LOGGER.error("API key missing. Check providing one via property or environment variable "
          + "'app.webclient.apikey.petrolstations'");
    }

    uriBuilderFacade
        .setHost("creativecommons.tankerkoenig.de")
        .setBasePath("/json/list.php")
        .putKeyWithSingleValue("sort", "dist")
        .putKeyWithSingleValue("type", "all")
        .putKeyWithSingleValue("apikey", petrolStationApiKey);
  }

  @Override
  public URI getUri() {
    return uriBuilderFacade.build();
  }

  @Override
  public void setGeoLocation(double lat, double lng) {
    uriBuilderFacade
        .putKeyWithSingleValue("lat", lat)
        .putKeyWithSingleValue("lng", lng);
  }

  @Override
  public void setDistance(double searchRadius) {
    uriBuilderFacade.putKeyWithSingleValue("rad", searchRadius);
  }

  @Override
  public void setAddressLocation(
      String street, String houseNumber, String city, String postCode) {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
