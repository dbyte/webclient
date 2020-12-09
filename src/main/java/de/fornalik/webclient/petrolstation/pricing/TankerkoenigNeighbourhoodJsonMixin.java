package de.fornalik.webclient.petrolstation.pricing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.fornalik.webclient.location.address.Address;
import de.fornalik.webclient.location.address.Geo;

abstract class TankerkoenigNeighbourhoodJsonMixin {

  @JsonUnwrapped private Address address;
  @JsonUnwrapped private Geo geo;

  @SuppressWarnings("unused")
  private TankerkoenigNeighbourhoodJsonMixin(
      @JsonProperty("id") String id,
      @JsonProperty("brand") String brand,
      @JsonProperty("isOpen") Boolean isOpen,
      @JsonProperty("diesel") Double diesel,
      @JsonProperty("e5") Double e5,
      @JsonProperty("e10") Double e10) {}

  @SuppressWarnings("unused")
  private TankerkoenigNeighbourhoodJsonMixin(
      @JsonProperty("name") String name,
      @JsonProperty("street") String street,
      @JsonProperty("houseNumber") String houseNumber,
      @JsonProperty("place") String city,
      @JsonProperty("postCode") String postCode) {}

  @SuppressWarnings("unused")
  private TankerkoenigNeighbourhoodJsonMixin(
      @JsonProperty("lat") double latitude,
      @JsonProperty("lng") double longitude,
      @JsonProperty("dist") Double distance) {}
}