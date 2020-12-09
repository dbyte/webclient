package de.fornalik.webclient.petrolstation.messaging;

import de.fornalik.webclient.messaging.common.MessageContent;
import de.fornalik.webclient.petrolstation.data.PetrolStation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(staticName = "of")
public class PetrolStationMessageContentAdapter implements MessageContent {

  @NonNull private final PetrolStation petrolStation;

  @Override
  public String getTitle() {
    return "Neuer Preis!";
  }

  @Override
  public String getMessage() {
    return petrolStation.getAddress().getName()
        + "\n"
        + "Diesel: " + petrolStation.getDiesel()
        + "\n"
        + (petrolStation.getIsOpen() ? "jetzt geöffnet" : "geschlossen");
  }
}
