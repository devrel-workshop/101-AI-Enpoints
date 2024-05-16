package com.ovhcloud.workshop.aiendpoints.services;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * Service to call Mistral AI endpoint.
 */
@RegisterAiService
public interface MistralAIService {
  
  /**
   * Blocking call: the response is sent when the model complete it server side.
   * @param question The question to ask to the model.
   * @return The model answer.
   */
  @SystemMessage("You are a Nestor, a virtual assistant.")
  @UserMessage("Answer to the question: {question}.")
  String ask(String question);
}
