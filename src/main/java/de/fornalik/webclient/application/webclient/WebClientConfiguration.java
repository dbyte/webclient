package de.fornalik.webclient.application.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
class WebClientConfiguration implements WebFluxConfigurer {

  @Autowired private Jackson2ObjectMapperBuilder objectMapperBuilder;

  @Bean
  @Scope("prototype")
  DefaultUriBuilderFactory uriBuilderFactory() {
    return new DefaultUriBuilderFactory();
  }

  @Bean("parameterMap")
  @Scope("prototype")
  MultiValueMap<String, String> uriParameterMap() {
    return new LinkedMultiValueMap<>();
  }
}