package com.ovhcloud.ai.quarkus.chatbot;

import com.ovhcloud.ai.quarkus.chatbot.service.AIAdvancedService;
import com.ovhcloud.ai.quarkus.chatbot.service.AISimpleService;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 * Main entry for the simple chatbot exercise.
 * The goal is to have a endpoint as following :
 * http://localhost:8080/chatbot/simple.
 * The verb to use is POST.
 * The payload is the question to send to the LLM.
 * The response is the answer given by the LLM.
 * see https://quarkus.io/guides/rest
 */
@Path("/chatbot")
public class AdvancedResource {
  // Inject the AIAdvancedService service

  // Declare a POST method with the "advanced" path and activate the streaming
  // mode
  // Call the askAQuestion method of the AISimpleService service and stream the
  // answer, see https://quarkus.io/guides/getting-started-reactive

}