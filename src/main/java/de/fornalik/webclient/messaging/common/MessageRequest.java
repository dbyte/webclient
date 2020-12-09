package de.fornalik.webclient.messaging.common;

import de.fornalik.webclient.application.webclient.Request;

public interface MessageRequest extends Request {

  void setMessage(MessageContent content);
}
