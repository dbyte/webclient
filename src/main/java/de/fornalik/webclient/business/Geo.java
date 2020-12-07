package de.fornalik.webclient.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@Scope("prototype")
public class Geo {

  private Double latitude;
  private Double longitude;
  private Double distance;

  private Geo() {
  }

  public Geo(Double latitude, Double longitude, Double distance) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.distance = distance;
  }

  @JsonCreator
  public static Geo createNullGeo() {
    return new Geo();
  }

  @JsonCreator
  public static Geo createGeo(
      @JsonProperty("lat") Double latitude,
      @JsonProperty("lng") Double longitude,
      @JsonProperty("dist") Double distance) {
    return new Geo(latitude, longitude, distance);
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Double getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Geo.class.getSimpleName() + "[", "]")
        .add("latitude=" + latitude)
        .add("longitude=" + longitude)
        .add("distance=" + distance)
        .toString();
  }
}
