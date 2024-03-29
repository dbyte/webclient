package de.fornalik.webclient.location.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fornalik.webclient.location.address.Geo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
final class GoogleGeocodingResponseMapper implements Converter<String, Mono<Geo>> {

  @NonNull private final ObjectMapper mapper;

  @Override
  public Mono<Geo> convert(@NonNull String json) {
    Dto dto;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    try {
      dto = mapper.readValue(json, Dto.class);
      return Mono.just(dto.getAsGeo());
    }
    catch (JsonProcessingException | IllegalStateException ex) {
      return Mono.error(new RuntimeException(ex.getMessage()));
    }
  }

  /**
   * Class provides object relational mapping support for JSON. It must correlate with the
   * root level json object of the Google Geocoding response.
   */
  private static class Dto {
    @JsonProperty("results") private final ArrayList<ResultDto> results = new ArrayList<>();
    @JsonProperty("status") private String status;
    @JsonProperty("error_message") private String message;

    private Geo getAsGeo() {
      validateStructure();

      return Geo.of(
          results.get(0).geometryDto.locationDto.latitude,
          results.get(0).geometryDto.locationDto.longitude);
    }

    private void validateStructure() {
      if (!"OK".equals(status)) {
        throw new IllegalStateException("REST service response signaled error: " + message);
      }

      if (results.isEmpty()) {
        throw new IllegalStateException("No geo results in JSON reponse.");
      }

      if (results.get(0).geometryDto == null
          || results.get(0).geometryDto.locationDto == null) {
        throw new IllegalStateException("No geo location data in JSON reponse.");
      }
    }
  }

  /**
   * Class provides object relational mapping support for JSON. It represents the json array
   * "results" of the Google Geocoding response.
   */
  private static class ResultDto {
    @JsonProperty("geometry") private GeometryDto geometryDto;
  }

  /**
   * Class provides object relational mapping support for JSON. It represents the "geometry"
   * json object within one element of json array "results" of the Google Geocoding response.
   */
  private static class GeometryDto {
    @JsonProperty("location") private LocationDto locationDto;
    @JsonProperty("location_type") private String locationType;
  }

  /**
   * Class provides object relational mapping support for JSON. It represents the "location"
   * json object within the json object "geometry" of the Google Geocoding response.
   */
  private static class LocationDto {
    @JsonProperty("lat") private Double latitude;
    @JsonProperty("lng") private Double longitude;
  }
}
