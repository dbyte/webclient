package de.fornalik.webclient.petrolstation.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.fornalik.webclient.location.address.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of", onConstructor = @__(@JsonCreator))
@Builder
@Data
public final class PetrolStation {

  @NonNull private final String id;
  @NonNull private final String brand;
  @NonNull private final Boolean isOpen;
  private final Double diesel;
  private final Double e5;
  private final Double e10;

  @NonFinal private Address address;
}
