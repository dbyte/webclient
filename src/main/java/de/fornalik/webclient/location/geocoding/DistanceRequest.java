package de.fornalik.webclient.location.geocoding;

import de.fornalik.webclient.application.webclient.Request;

public interface DistanceRequest extends Request {

  void setDistance(double distance);
}
