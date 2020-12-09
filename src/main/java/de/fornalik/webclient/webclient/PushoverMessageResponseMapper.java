package de.fornalik.webclient.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
class PushoverMessageResponseMapper implements Converter<String, Mono<Void>> {

  private final ObjectMapper mapper;

  @Override
  @NonNull
  public Mono<Void> convert(String json) {
    Dto dto;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    try {
      dto = mapper.readValue(Objects.requireNonNull(json), Dto.class);
    }
    catch (JsonProcessingException | IllegalStateException ex) {
      return Mono.error(new RuntimeException(ex.getMessage()));
    }

    if (dto.status == 0) {
      return Mono.error(new RuntimeException(
          "REST service response signaled error: " + deserializeErrors(dto.errors)));
    }

    return Mono.empty();
  }

  private String deserializeErrors(List<String> errors) {
    if (errors == null || errors.isEmpty()) return "No error list found.";
    return String.join("\n", errors);
  }

  private static class Dto {
    @JsonProperty("errors") private final List<String> errors = new ArrayList<>();
    @JsonProperty("status") private int status;
    @JsonProperty("secret") private String secret;
    @JsonProperty("request") private String requestId;
  }
}
