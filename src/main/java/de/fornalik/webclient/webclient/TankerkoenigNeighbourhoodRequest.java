package de.fornalik.webclient.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TankerkoenigNeighbourhoodRequest implements PetrolStationNeighbourhoodRequest {

  private final UriBuilderFacade uriBuilderFacade;
  @Autowired @Value("${app.webclient.apikey.petrolstations:}") private String petrolStationApiKey;

  @PostConstruct
  private void init() {
    setDefaultParams();
  }

  private void setDefaultParams() {
    if (petrolStationApiKey.isEmpty()) {
      log.error("API key missing. Check providing one via property or environment variable "
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
