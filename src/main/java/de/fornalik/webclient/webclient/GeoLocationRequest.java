package de.fornalik.webclient.webclient;

public interface GeoLocationRequest extends Request {

  void setGeoLocation(double lat, double lng);
}
