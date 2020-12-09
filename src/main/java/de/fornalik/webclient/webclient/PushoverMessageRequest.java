package de.fornalik.webclient.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

  private final UriBuilderFacade uriBuilderFacade;
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
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(uriBuilderFacade.getParameterMap().toSingleValueMap());
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
