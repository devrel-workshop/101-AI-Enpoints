package com.ovhcloud.ai.quarkus.chatbot.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;


/**
 * Service to send prompt to the LLM.
 * The service use the RegisterAiService annotation, see https://docs.quarkiverse.io/quarkus-langchain4j/dev/ai-services.html
 * Set a system message to indicate that this a virtual assistant named Nestor
 * Set a user message to answer to questions. 
 * The response must be in a streaming mode.
 */
public interface AIAdvancedService {
  // Set the System and User message and activate the streaming mode
}