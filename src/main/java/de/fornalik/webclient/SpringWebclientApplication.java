package de.fornalik.webclient;

import de.fornalik.webclient.application.MainController;
import de.fornalik.webclient.location.address.Address;
import de.fornalik.webclient.location.address.Geo;
import de.fornalik.webclient.messaging.common.MessageContent;
import de.fornalik.webclient.petrolstation.data.PetrolStation;
import de.fornalik.webclient.petrolstation.messaging.PetrolStationMessageContentAdapter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import reactor.core.publisher.ConnectableFlux;
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
    // Flux<PetrolStation> petrolStationFlux = Flux.empty();
    ConnectableFlux<PetrolStation> petrolStationFlux = mainController
        .findPetrolStations(demoData.getT1())
        .publish();

    // Request Geocoding Webservice
    // Mono<Geo> geoMono = Mono.empty();
    Mono<Geo> geoMono = mainController.findGeoLocation(demoData.getT2());

    // Send pushmessage with last PetrolStation content - defer until last occuring PetrolStation.
    Mono<Void> messageMono = petrolStationFlux
        .last()
        .flatMap(station -> {
          MessageContent messageContent = PetrolStationMessageContentAdapter.of(station);
          return mainController.sendPushoverMessage(messageContent);
        });

    // Start hotstream (messageMono is listening to petrolStationFlux events)
    petrolStationFlux.connect();

    // Shutdown after both streams have terminated.
    Flux.concat(messageMono, geoMono)
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
