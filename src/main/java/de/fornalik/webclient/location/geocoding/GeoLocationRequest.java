package de.fornalik.webclient.location.geocoding;

import de.fornalik.webclient.application.webclient.Request;

public interface GeoLocationRequest extends Request {

  void setGeoLocation(double lat, double lng);
}
