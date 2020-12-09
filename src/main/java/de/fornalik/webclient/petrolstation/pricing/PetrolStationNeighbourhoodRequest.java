package de.fornalik.webclient.petrolstation.pricing;

import de.fornalik.webclient.location.geocoding.AddressRequest;
import de.fornalik.webclient.location.geocoding.DistanceRequest;
import de.fornalik.webclient.location.geocoding.GeoLocationRequest;

public interface PetrolStationNeighbourhoodRequest
    extends GeoLocationRequest, AddressRequest, DistanceRequest {
}
