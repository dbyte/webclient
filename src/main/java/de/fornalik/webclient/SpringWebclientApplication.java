package de.fornalik.webclient;

import de.fornalik.webclient.application.MainController;
import de.fornalik.webclient.business.Address;
import de.fornalik.webclient.business.Geo;
import de.fornalik.webclient.business.PetrolStation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@SpringBootApplication
public class SpringWebclientApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(SpringWebclientApplication.class).run(args);
    context.registerShutdownHook();

    Tuple2<Geo, Address> demoData = createDemoData();
    MainController mainController = context.getBean(MainController.class);

    // Request Petrol Station Webservice
    Flux<PetrolStation> petrolStationFlux = mainController.findPetrolStations(demoData.getT1());

    // Request Geocoding Webservice
    Mono<Geo> geoMono = mainController.findGeoLocation(demoData.getT2());

    // Shutdown after both streams have terminated.
    Flux.concat(petrolStationFlux, geoMono)
        .doFinally(signal -> context.stop())
        .subscribe(null, Throwable::printStackTrace);
  }

  private static Tuple2<Geo, Address> createDemoData() {
    Geo geo = Geo.of(52.408306, 10.7720078, 7.0);

    Address address = Address.builder()
        .street("Wasserburger Str.")
        .houseNumber("5")
        .city("Grafing")
        .postCode("85567")
        .build();

    return Tuples.of(geo, address);
  }
}
