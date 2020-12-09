package de.fornalik.webclient.location.geocoding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class GeocodingWebClientConfiguration implements WebFluxConfigurer {

  @Autowired private Jackson2ObjectMapperBuilder objectMapperBuilder;

  @Bean
  GoogleGeocodingResponseMapper googleGeocodingResponseMapper() {
    return new GoogleGeocodingResponseMapper(objectMapperBuilder.build());
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(googleGeocodingResponseMapper());
  }
}
