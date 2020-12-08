package de.fornalik.webclient.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@Scope("prototype")
public class PetrolStation {
  @JsonProperty("id") private String id;
  @JsonProperty("brand") private String brand;
  @JsonProperty("isOpen") private Boolean isOpen;
  private Address address;

  @JsonProperty("diesel") private Double diesel;
  @JsonProperty("e5") private Double e5;
  @JsonProperty("e10") private Double e10;

  @Autowired
  public PetrolStation() {
    //this(new Address());
  }

  /*@Autowired
  public PetrolStation(Address address) {
    this.address = address;
  }*/

  public String getBrand() {
    return brand;
  }

  /*@JsonProperty("name")
  private void setAdrName(String name) {
    this.addressBuilder.name(name);
  }*/
  /*@JsonProperty("name")
  private void setAdrName(String name) {
    this.address.setName(name);
  }*/

  /*@JsonProperty("street")
  private void setAdrStreet(String street) {
    this.address.setStreet(street);
  }

  @JsonProperty("houseNumber")
  private void setAdrHouseNumber(String houseNumber) {
    this.address.setHouseNumber(houseNumber);
  }

  @JsonProperty("place")
  private void setAdrCity(String city) {
    this.address.setCity(city);
  }

  @JsonProperty("postCode")
  private void setAdrPostCode(String postCode) {
    this.address.setPostCode(postCode);
  }*/

  @JsonProperty("lat")
  private void setGeoLatitude(Double lat) {
    //    this.address.setGeoLatitude(lat);
  }

  @JsonProperty("lng")
  private void setGeoLongitude(Double lng) {
    //    this.address.setGeoLongitude(lng);
  }

  @JsonProperty("dist")
  private void setDistance(Double distance) {
    //    this.address.setGeoDistance(distance);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PetrolStation.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("brand='" + brand + "'")
        .add("isOpen=" + isOpen)
        .add("diesel=" + diesel)
        .add("e5=" + e5)
        .add("e10=" + e10)
        .add("address=" + address)
        .toString();
  }
}
