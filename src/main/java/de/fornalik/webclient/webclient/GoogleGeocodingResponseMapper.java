package de.fornalik.webclient.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fornalik.webclient.business.Geo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Objects;

@RequiredArgsConstructor
public class GoogleGeocodingResponseMapper implements Converter<String, Mono<Geo>> {

  private final ObjectMapper mapper;

  @Override
  @NonNull
  public Mono<Geo> convert(String json) {
    Dto dto;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    try {
      dto = mapper.readValue(Objects.requireNonNull(json), Dto.class);
    }
    catch (JsonProcessingException ex) {
      return Mono.error(new RuntimeException(ex.getMessage()));
    }

    if (!"OK".equals(dto.status)) {
      return Mono.error(new IllegalStateException(
          "REST service response signaled error: " + dto.message));
    }

    if (dto.results.isEmpty()) {
      return Mono.error(new IllegalStateException("No geo results in JSON reponse."));
    }

    if (dto.results.get(0).geometryDto == null
        || dto.results.get(0).geometryDto.locationDto == null) {
      return Mono.error(new IllegalStateException("No location data in JSON reponse."));
    }

    return Mono.just(dto.results.get(0).getAsGeo());
  }

  /**
   * Class provides object relational mapping support for JSON. It must correlate with the
   * root level json object of the Google Geocoding response.
   */
  private static class Dto {
    @JsonProperty("results") private final ArrayList<ResultDto> results = new ArrayList<>();
    @JsonProperty("status") private String status;
    @JsonProperty("error_message") private String message;
  }

  /**
   * Class provides object relational mapping support for JSON. It represents the json array
   * "results" of the Google Geocoding response.
   */
  private static class ResultDto {
    @JsonProperty("geometry") private GeometryDto geometryDto;

    private Geo getAsGeo() {
      return Geo.of(geometryDto.locationDto.latitude, geometryDto.locationDto.longitude);
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
}
