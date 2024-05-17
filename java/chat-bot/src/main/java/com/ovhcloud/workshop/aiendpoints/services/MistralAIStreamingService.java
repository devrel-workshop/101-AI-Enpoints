package com.ovhcloud.workshop.aiendpoints.services;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;

/**
 * Service to call Mistral AI endpoint in streaming mode  .
 */
@RegisterAiService
public interface MistralAIStreamingService {
  
/**
   * Streaming call: the response is sent bloc by bloc by the model.
   * @param question The question to ask to the model.
   * @return The model answer.
   */
  @SystemMessage("You are a Nestor, a virtual assistant.")
  @UserMessage("Answer to the question: {question}.")
  Multi<String> ask(String question);
}
