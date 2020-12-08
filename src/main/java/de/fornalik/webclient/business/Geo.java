package de.fornalik.webclient.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Data
public class Geo {

  @JsonProperty("lat")
  private final double latitude;

  @JsonProperty("lng")
  private final double longitude;

  /**
   * Distance relative to some other point of interest, may it be an address or a second geo
   * location. Explicitly null if no relation needed.
   */
  @JsonProperty("dist")
  private Double distance;

  @JsonCreator
  public static Geo of(double latitude, double longitude, double distance) {
    return new Geo(latitude, longitude, distance);
  }
}
