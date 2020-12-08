package de.fornalik.webclient.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of", onConstructor = @__(@JsonCreator))
@Builder
@Data
public class PetrolStation {
  @JsonProperty("id")
  private final String id;

  @JsonProperty("brand")
  private final String brand;

  @JsonProperty("isOpen")
  private final Boolean isOpen;

  @JsonProperty("diesel")
  private final Double diesel;

  @JsonProperty("e5")
  private final Double e5;

  @JsonProperty("e10")
  private final Double e10;

  @JsonUnwrapped
  @NonFinal
  private Address address;
}
