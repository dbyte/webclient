package de.fornalik.webclient.messaging.pushover;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fornalik.webclient.application.webclient.UriBuilderFacade;
import de.fornalik.webclient.messaging.common.MessageContent;
import de.fornalik.webclient.messaging.common.MessageRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
class PushoverMessageRequest implements MessageRequest {

  @NonNull private final UriBuilderFacade uriBuilderFacade;
  @NonNull private final ObjectMapper bodyMapper;
  @Value("${app.webclient.apikey.pushover:}") private String pushoverApiKey;
  @Value("${app.webclient.userId.pushover:}") private String pushoverUserId;

  @PostConstruct
  private void init() {
    validateAccessData();
    setDefaultParams();
  }

  private void validateAccessData() {
    if (pushoverApiKey.isEmpty()) {
      log.error("API key missing. Check providing one via property or environment variable "
          + "'app.webclient.apikey.pushover'");
    }
    if (pushoverUserId.isEmpty()) {
      log.error("User ID missing. Check providing one via property or environment variable "
          + "'app.webclient.userId.pushover'");
    }
  }

  private void setDefaultParams() {
    uriBuilderFacade
        .setHost("api.pushover.net")
        .setBasePath("/1/messages.json")
        .putKeyWithSingleValue("token", pushoverApiKey)
        .putKeyWithSingleValue("user", pushoverUserId);
  }

  @Override
  public void setMessage(MessageContent content) {
    uriBuilderFacade
        .putKeyWithSingleValue("title", content.getTitle())
        .putKeyWithSingleValue("message", content.getMessage());
  }

  @Override
  public String getBody() {
    try {
      return bodyMapper.writeValueAsString(uriBuilderFacade.getFlattenedParameterMap());
    }
    catch (JsonProcessingException ex) {
      throw new RuntimeException("Request map could not be converted to JSON string.");
    }
  }

  @Override
  public URI getUri() {
    return uriBuilderFacade.buildParameterless();
  }
}
