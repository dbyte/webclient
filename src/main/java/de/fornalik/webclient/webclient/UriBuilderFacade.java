package de.fornalik.webclient.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.List;

@Component
@Scope("prototype")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UriBuilderFacade {

  private final MultiValueMap<String, String> parameterMap;
  private final UriBuilderFactory uriBuilderFactory;
  private String host;
  private String basePath;

  MultiValueMap<String, String> getParameterMap() {
    return parameterMap;
  }

  UriBuilderFacade setHost(@NonNull String host) {
    this.host = host;
    return this;
  }

  UriBuilderFacade setBasePath(@NonNull String basePath) {
    this.basePath = basePath;
    return this;
  }

  UriBuilderFacade putKeyWithSingleValue(@NonNull String key, @NonNull String value) {
    parameterMap.put(key, List.of(value));
    return this;
  }

  UriBuilderFacade putKeyWithSingleValue(@NonNull String key, double value) {
    parameterMap.put(key, List.of(String.valueOf(value)));
    return this;
  }

  URI build(@NonNull String template) {
    return computeBaseUri()
        .query(template)
        .build(parameterMap.toSingleValueMap());
  }

  URI build() {
    return computeBaseUri()
        .queryParams(parameterMap)
        .build();
  }

  URI buildParameterless() {
    return computeBaseUri().build();
  }

  private UriBuilder computeBaseUri() {
    return uriBuilderFactory.builder()
        .scheme("https")
        .host(host)
        .path(basePath);
  }
}
