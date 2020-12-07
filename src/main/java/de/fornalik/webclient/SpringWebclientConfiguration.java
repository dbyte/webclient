package de.fornalik.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fornalik.webclient.webclient.GoogleGeocodingResponseMapper;
import de.fornalik.webclient.webclient.TankerkoenigNeighbourhoodResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class SpringWebclientConfiguration implements WebFluxConfigurer {

  @Autowired private ObjectMapper jsonMapper;

  @Bean
  TankerkoenigNeighbourhoodResponseMapper petrolStationsNeighbourhoodResponseConverter() {
    return new TankerkoenigNeighbourhoodResponseMapper(jsonMapper);
  }

  @Bean
  GoogleGeocodingResponseMapper googleGeocodingResponseMapper() {
    return new GoogleGeocodingResponseMapper(jsonMapper);
  }

  @Bean
  @Scope("prototype")
  DefaultUriBuilderFactory uriBuilderFactory() {
    return new DefaultUriBuilderFactory();
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(petrolStationsNeighbourhoodResponseConverter());
    registry.addConverter(googleGeocodingResponseMapper());
  }
}