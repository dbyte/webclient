package de.fornalik.webclient.petrolstation.pricing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fornalik.webclient.location.address.Address;
import de.fornalik.webclient.location.address.Geo;
import de.fornalik.webclient.petrolstation.data.PetrolStation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
final class TankerkoenigNeighbourhoodResponseMapper
    implements Converter<String, Flux<PetrolStation>> {

  @NonNull private final ObjectMapper mapper;

  @Override
  public Flux<PetrolStation> convert(@NonNull String json) {
    Dto dto;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.addMixIn(PetrolStation.class, TankerkoenigNeighbourhoodJsonMixin.class);
    mapper.addMixIn(Address.class, TankerkoenigNeighbourhoodJsonMixin.class);
    mapper.addMixIn(Geo.class, TankerkoenigNeighbourhoodJsonMixin.class);

    try {
      dto = mapper.readValue(json, Dto.class);
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
