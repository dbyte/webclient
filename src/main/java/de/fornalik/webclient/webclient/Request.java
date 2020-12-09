package de.fornalik.webclient.webclient;

import java.net.URI;

interface Request {

  URI getUri();

  default String getBody() {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
