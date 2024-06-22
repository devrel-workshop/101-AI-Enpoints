package com.ovhcloud.workshop.aiendpoints.resources;

import org.jboss.resteasy.reactive.RestQuery;
import com.ovhcloud.workshop.aiendpoints.services.MistralAIService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Endpoint to use the chatbot.
 */
@Path("/chatbot")
public class ChatbotBlockingResource {

  @Inject
  private MistralAIService aiService;

  private static String robot = "🤖:\n";

  /**
   * Use the chat in a blocking way (i.e wait that the model send the complete
   * response)
   * 
   * @return The model response.
   */
  @Path("/blocking")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String blocking(@RestQuery("question") String question) {
    return robot + aiService.ask(question);
  }
}
