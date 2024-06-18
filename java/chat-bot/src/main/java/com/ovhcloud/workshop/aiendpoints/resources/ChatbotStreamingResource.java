package com.ovhcloud.workshop.aiendpoints.resources;

import java.time.Duration;
import org.jboss.resteasy.reactive.RestQuery;
import com.ovhcloud.workshop.aiendpoints.services.MistralAIStreamingService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * Endpoint to use the chatbot.
 */
@Path("/chatbot")
public class ChatbotStreamingResource {

  @Inject
  private MistralAIStreamingService aiService;

  private static String robot = "🤖:\n";

  /**
   * Use the chat in a streaming way
   * 
   * @return The model response.
   */
  @Path("streaming")
  @GET
  public Multi<String> streaming(@RestQuery("question") String question) {
    Multi<String> res = Multi.createBy()
      .concatenating()
      .streams(Multi.createFrom()
                      .item(robot), aiService.ask(question)
      );

    return res
            .onItem()
            .call(i -> Uni.createFrom()
                                        .nullItem()
                                        .onItem()
                                        .delayIt()
                                        .by(Duration.ofMillis(200))
            );
  }
}
