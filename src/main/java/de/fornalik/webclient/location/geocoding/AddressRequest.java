package de.fornalik.webclient.location.geocoding;

import de.fornalik.webclient.application.webclient.Request;

public interface AddressRequest extends Request {

  void setAddressLocation(
      String street,
      String houseNumber,
      String city,
      String postCode);
}
