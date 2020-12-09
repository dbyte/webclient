package de.fornalik.webclient.location.geocoding;

import de.fornalik.webclient.application.webclient.UriBuilderFacade;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

@Component
@Qualifier("geocodingRequest")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
final class GoogleGeocodingRequest implements AddressRequest {

  private static final String STREET_KEY = "street";
  private static final String HOUSENUMBER_KEY = "houseNumber";
  private static final String POSTCODE_KEY = "postCode";
  private static final String CITY_KEY = "city";
  private static final String REGION_KEY = "region";
  private static final String APIKEY_KEY = "key";

  @Value("${app.webclient.apikey.geocoding:}") private String googleGeocodingApiKey;
  @NonNull private final UriBuilderFacade uriBuilderFacade;

  @PostConstruct
  private void init() {
    setDefaultParams();
  }

  private void setDefaultParams() {
    if (googleGeocodingApiKey.isEmpty()) {
      log.error("API key missing. Check providing one via property or environment variable "
          + "'app.webclient.apikey.geocoding'");
    }

    uriBuilderFacade
        .setHost("maps.googleapis.com")
        .setBasePath("/maps/api/geocode/json")
        .putKeyWithSingleValue(REGION_KEY, "de")
        .putKeyWithSingleValue(APIKEY_KEY, googleGeocodingApiKey);
  }

  private String getQueryTemplate() {
    // Ex: region=de&address=Otto-P%C3%B6hling-Str+3,+10000+Berlin&key=abc-def-hijkl
    return String.format("%1$s={%1$s}&address={%2$s}+{%3$s},+{%4$s}+{%5$s}&%6$s={%6$s}",
        REGION_KEY, STREET_KEY, HOUSENUMBER_KEY, POSTCODE_KEY, CITY_KEY, APIKEY_KEY);
  }

  @Override
  @NonNull
  public void setAddressLocation(String street, String houseNumber, String city, String postCode) {
    uriBuilderFacade
        .putKeyWithSingleValue(STREET_KEY, street)
        .putKeyWithSingleValue(HOUSENUMBER_KEY, houseNumber)
        .putKeyWithSingleValue(CITY_KEY, city)
        .putKeyWithSingleValue(POSTCODE_KEY, postCode);
  }

  @Override
  public URI getUri() {
    return uriBuilderFacade.build(getQueryTemplate());
  }
}
