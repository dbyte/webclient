package de.fornalik.webclient.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of", onConstructor = @__(@JsonCreator))
@Builder
@Data
public class Address {

  @JsonProperty("name")
  private final String name;

  @NonNull
  @JsonProperty("street")
  private final String street;

  @JsonProperty("houseNumber")
  private final String houseNumber;

  @NonNull
  @JsonProperty("place")
  private final String city;

  @NonNull
  @JsonProperty("postCode")
  private final String postCode;

  /**
   * {@link Geo} location data for this address.
   * Explicitly null if no geo data areassociated with this address.
   */
  @NonFinal
  @JsonUnwrapped
  private Geo geo;
}
