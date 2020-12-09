package de.fornalik.webclient.petrolstation.pricing;

import de.fornalik.webclient.application.webclient.RequestBag;
import lombok.NonNull;
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
final class TankerkoenigNeighbourhoodRequest implements PetrolStationNeighbourhoodRequest {

  @NonNull private final RequestBag requestBag;
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

    requestBag
        .setHost("creativecommons.tankerkoenig.de")
        .setBasePath("/json/list.php")
        .putKeyWithSingleValue("sort", "dist")
        .putKeyWithSingleValue("type", "all")
        .putKeyWithSingleValue("apikey", petrolStationApiKey);
  }

  @Override
  public URI getUri() {
    return requestBag.buildUri();
  }

  @Override
  public void setGeoLocation(double lat, double lng) {
    requestBag
        .putKeyWithSingleValue("lat", lat)
        .putKeyWithSingleValue("lng", lng);
  }

  @Override
  public void setDistance(double searchRadius) {
    requestBag.putKeyWithSingleValue("rad", searchRadius);
  }

  @Override
  @NonNull
  public void setAddressLocation(
      String street, String houseNumber, String city, String postCode) {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
