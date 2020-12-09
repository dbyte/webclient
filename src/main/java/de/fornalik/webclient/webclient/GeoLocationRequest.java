package de.fornalik.webclient.webclient;

interface GeoLocationRequest extends Request {

  void setGeoLocation(double lat, double lng);
}
