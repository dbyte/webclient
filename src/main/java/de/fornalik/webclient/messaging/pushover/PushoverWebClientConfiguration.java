package de.fornalik.webclient.messaging.pushover;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class PushoverWebClientConfiguration implements WebFluxConfigurer {

  @Autowired private Jackson2ObjectMapperBuilder objectMapperBuilder;

  @Bean
  PushoverMessageResponseMapper pushoverMessageResponseMapper() {
    return new PushoverMessageResponseMapper(objectMapperBuilder.build());
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(pushoverMessageResponseMapper());
  }
}
