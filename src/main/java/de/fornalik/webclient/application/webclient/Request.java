package de.fornalik.webclient.application.webclient;

import java.net.URI;

public interface Request {

  URI getUri();

  default String getBody() {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
