package com.ovhcloud.workshop.aiendpoints.resources;

import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestStreamElementType;

import com.ovhcloud.workshop.aiendpoints.services.MistralAIService;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Endpoint to use the chatbot.
 */
@Path("/chatbot")
public class ChatBotResource {

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

  /**
   * Use the chat in a streaming way
   * 
   * @return The model response.
   */
  @Path("streaming")
  @GET
  public Multi<String> streaming(@RestQuery("question") String question) {
    return Multi.createBy().concatenating().streams(Multi.createFrom().item(robot), aiService.askStreaming(question));
  }
}
