package de.fornalik.webclient.application.webclient;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

/**
 * Utility class for common @{@link org.springframework.web.reactive.function.client.WebClient}
 * handling.
 */
@Slf4j
public class WebClientSupport {

  private WebClientSupport() {}

  public static Mono<String> processRawResponse(@NonNull ClientResponse response) {
    HttpStatus statusCode = response.statusCode();

    if (statusCode.equals(HttpStatus.OK)) {
      return response.bodyToMono(String.class);
    }

    else if (statusCode.isError()) {
      log.error(
          "HTTP response status {}: {}", statusCode.value(), statusCode.getReasonPhrase());

      /* Proceed converting body to JSON with our dedicated mapper as some services send a 400
      but also a JSON body with concrete reasons for the fail. We check for some status
      flags within our mappers and will signal errors right there. */
      return response.bodyToMono(String.class);
    }

    else {
      return Mono.error(new RuntimeException("Unexpected response error"));
    }
  }
}
