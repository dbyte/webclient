package de.fornalik.webclient.application.webclient;

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
import java.util.Map;

@Component
@Scope("prototype")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UriBuilderFacade {

  @NonNull private final MultiValueMap<String, String> parameterMap;
  @NonNull private final UriBuilderFactory uriBuilderFactory;
  private String host;
  private String basePath;

  public Map<String, String> getFlattenedParameterMap() {
    return parameterMap.toSingleValueMap();
  }

  public UriBuilderFacade setHost(@NonNull String host) {
    this.host = host;
    return this;
  }

  public UriBuilderFacade setBasePath(@NonNull String basePath) {
    this.basePath = basePath;
    return this;
  }

  public UriBuilderFacade putKeyWithSingleValue(@NonNull String key, @NonNull String value) {
    parameterMap.put(key, List.of(value));
    return this;
  }

  public UriBuilderFacade putKeyWithSingleValue(@NonNull String key, double value) {
    parameterMap.put(key, List.of(String.valueOf(value)));
    return this;
  }

  public URI build(@NonNull String template) {
    return computeBaseUri()
        .query(template)
        .build(getFlattenedParameterMap());
  }

  public URI build() {
    return computeBaseUri()
        .queryParams(parameterMap)
        .build();
  }

  public URI buildParameterless() {
    return computeBaseUri().build();
  }

  private UriBuilder computeBaseUri() {
    return uriBuilderFactory.builder()
        .scheme("https")
        .host(host)
        .path(basePath);
  }
}
