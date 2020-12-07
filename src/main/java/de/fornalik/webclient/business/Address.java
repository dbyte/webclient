package de.fornalik.webclient.business;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@Scope("prototype")
public class Address {
  private final Geo geo;
  private String name;
  private String street;
  private String houseNumber;
  private String city;
  private String postCode;

  public Address() {
    this(Geo.createNullGeo());
  }

  public Address(Geo geo) {
    this.geo = geo;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public void setName(String name) {
    this.name = name;
  }

  /*public void setGeoLatitude(Double lat) {
    geo.setLatitude(lat);
  }

  public void setGeoLongitude(Double lng) {
    geo.setLongitude(lng);
  }

  public void setGeoDistance(Double distance) {
    geo.setDistance(distance);
  }*/

  @Override
  public String toString() {
    return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("street='" + street + "'")
        .add("houseNumber='" + houseNumber + "'")
        .add("city='" + city + "'")
        .add("postCode='" + postCode + "'")
        .add("geo=" + geo)
        .toString();
  }
}
