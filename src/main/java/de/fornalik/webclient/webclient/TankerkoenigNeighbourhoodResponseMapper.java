package de.fornalik.webclient.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fornalik.webclient.business.PetrolStation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class TankerkoenigNeighbourhoodResponseMapper
    implements Converter<String, Flux<PetrolStation>> {

  private final ObjectMapper mapper;

  @Override
  @NonNull
  public Flux<PetrolStation> convert(String json) {
    Dto dto;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    try {
      dto = mapper.readValue(Objects.requireNonNull(json), Dto.class);
    }
    catch (JsonProcessingException ex) {
      return Flux.error(new RuntimeException(ex.getMessage()));
    }

    if (!dto.ok) {
      return Flux.error(new IllegalStateException(
          "REST service response signaled error: " + dto.message));
    }

    if (dto.stations == null) {
      return Flux.error(new IllegalStateException("No petrol stations in JSON reponse."));
    }

    return Flux.fromIterable(dto.stations);
    //        .delayElements(Duration.ofMillis(100));
  }

  private static class Dto {
    @JsonProperty("status") private String status;
    @JsonProperty("ok") private boolean ok;
    @JsonProperty("message") private String message;
    @JsonProperty("stations") private List<PetrolStation> stations;
  }
}
