package de.fornalik.webclient.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
public class UriBuilderFacade {

  private final MultiValueMap<String, String> parameterMap;
  private final UriBuilderFactory uriBuilderFactory;
  private String host;
  private String basePath;

  @Autowired
  public UriBuilderFacade(UriBuilderFactory uriBuilderFactory) {
    this.uriBuilderFactory = uriBuilderFactory;
    this.parameterMap = new LinkedMultiValueMap<>();
  }

  UriBuilderFacade setHost(String host) {
    this.host = Objects.requireNonNull(host, "host must not be null");
    return this;
  }

  UriBuilderFacade setBasePath(String basePath) {
    this.basePath = Objects.requireNonNull(basePath, "basePath must not be null");
    return this;
  }

  UriBuilderFacade putKeyWithSingleValue(String key, String value) {
    parameterMap.put(key, List.of(value));
    return this;
  }

  UriBuilderFacade putKeyWithSingleValue(String key, double value) {
    parameterMap.put(key, List.of(String.valueOf(value)));
    return this;
  }

  URI build(String template) {
    return processPreBuild()
        .query(template)
        .build(parameterMap.toSingleValueMap());
  }

  URI build() {
    return processPreBuild()
        .queryParams(parameterMap)
        .build();
  }

  private org.springframework.web.util.UriBuilder processPreBuild() {
    return uriBuilderFactory.builder()
        .scheme("https")
        .host(host)
        .path(basePath);
  }
}
