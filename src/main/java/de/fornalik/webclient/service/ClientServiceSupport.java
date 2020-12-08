package de.fornalik.webclient.service;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

/**
 * Utility class for common @{@link org.springframework.web.reactive.function.client.WebClient}
 * handling.
 */
public class ClientServiceSupport {

  private ClientServiceSupport() {}

  static Mono<String> processRawResponse(@NonNull ClientResponse response) {
    HttpStatus statusCode = response.statusCode();

    if (statusCode.equals(HttpStatus.OK)) {
      return response.bodyToMono(String.class);
    }
    else if (statusCode.isError()) {
      return Mono.error(new RuntimeException(String.format(
          "Client response error %d %s",
          statusCode.value(),
          statusCode.getReasonPhrase())));
    }
    else {
      return Mono.error(new RuntimeException("Unexpected response error"));
    }
  }
}
