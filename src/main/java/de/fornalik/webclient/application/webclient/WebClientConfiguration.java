package de.fornalik.webclient.application.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
class WebClientConfiguration implements WebFluxConfigurer {

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