package de.fornalik.webclient.business;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Builder
@Data
public class Address {

  private final String name;
  @NonNull private final String street;
  private final String houseNumber;
  @NonNull private final String city;
  @NonNull private final String postCode;

  /**
   * {@link Geo} location data for this address.
   * Explicitly null if no geo data areassociated with this address.
   */
  @NonFinal private Geo geo;
}
