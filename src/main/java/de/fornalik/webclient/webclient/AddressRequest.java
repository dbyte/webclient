package de.fornalik.webclient.webclient;

public interface AddressRequest extends Request {

  void setAddressLocation(
      String street,
      String houseNumber,
      String city,
      String postCode);
}
