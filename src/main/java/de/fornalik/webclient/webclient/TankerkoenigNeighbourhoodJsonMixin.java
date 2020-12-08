package de.fornalik.webclient.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;

abstract class TankerkoenigNeighbourhoodJsonMixin {

  @JsonUnwrapped Address address;
  @JsonUnwrapped Geo geo;

  @SuppressWarnings("unused")
  TankerkoenigNeighbourhoodJsonMixin(
      @JsonProperty("id") String id,
      @JsonProperty("brand") String brand,
      @JsonProperty("isOpen") Boolean isOpen,
      @JsonProperty("diesel") Double diesel,
      @JsonProperty("e5") Double e5,
      @JsonProperty("e10") Double e10) {}

  @SuppressWarnings("unused")
  TankerkoenigNeighbourhoodJsonMixin(
      @JsonProperty("name") String name,
      @JsonProperty("street") String street,
      @JsonProperty("houseNumber") String houseNumber,
      @JsonProperty("place") String city,
      @JsonProperty("postCode") String postCode) {}

  @SuppressWarnings("unused")
  TankerkoenigNeighbourhoodJsonMixin(
      @JsonProperty("lat") double latitude,
      @JsonProperty("lng") double longitude,
      @JsonProperty("dist") Double distance) {}
}