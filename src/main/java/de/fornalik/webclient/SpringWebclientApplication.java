package de.fornalik.webclient;

import de.fornalik.webclient.application.MainController;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringWebclientApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(SpringWebclientApplication.class);
  private static ConfigurableApplicationContext context;

  public static void main(String[] args) {
    context = new SpringApplicationBuilder(SpringWebclientApplication.class).run(args);
    context.registerShutdownHook();

    MainController mainController = context.getBean(MainController.class);
    mainController.doRequestPetrolStations(new Geo(52.408306, 10.7720078, 7.0));

    Address address = new Address();
    address.setStreet("Wasserburger Str.");
    address.setHouseNumber("4");
    address.setCity("Grafing");
    address.setPostCode("85567");
    mainController.doRequestGeoLocation(address);
  }
}
