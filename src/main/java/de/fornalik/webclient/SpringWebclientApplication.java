package de.fornalik.webclient;

import de.fornalik.webclient.application.MainController;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringWebclientApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(SpringWebclientApplication.class)
            .run(args);
    context.registerShutdownHook();

    MainController mainController = context.getBean(MainController.class);
    mainController.doRequestPetrolStations(Geo.of(52.408306, 10.7720078, 7.0));

    Address address = Address.builder()
        .street("Wasserburger Str.")
        .houseNumber("5")
        .city("Grafing")
        .postCode("85567")
        .build();
    mainController.doRequestGeoLocation(address);
  }
}
