package de.fornalik.webclient.business;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Data
public class Geo {

  private final double latitude;
  private final double longitude;

  /**
   * Distance relative to some other point of interest, may it be an address or a second geo
   * location. Explicitly null if no relation needed.
   */
  private Double distance;

  public static Geo of(double latitude, double longitude, double distance) {
    return new Geo(latitude, longitude, distance);
  }
}
