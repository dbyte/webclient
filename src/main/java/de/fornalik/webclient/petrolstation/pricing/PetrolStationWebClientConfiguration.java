package de.fornalik.webclient.petrolstation.pricing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class PetrolStationWebClientConfiguration implements WebFluxConfigurer {

  @Autowired private Jackson2ObjectMapperBuilder objectMapperBuilder;

  @Bean
  TankerkoenigNeighbourhoodResponseMapper petrolStationsNeighbourhoodResponseMapper() {
    return new TankerkoenigNeighbourhoodResponseMapper(objectMapperBuilder.build());
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(petrolStationsNeighbourhoodResponseMapper());
  }
}
