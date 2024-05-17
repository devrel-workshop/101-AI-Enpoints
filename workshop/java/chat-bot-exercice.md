## 🤖 Steps to do the chat bot exercices 💬

## ⚠️ Prerequisites ⚠️

Before begging, we assume that you have created the `chat-bot` project, see section `Module 1: Chat bot with AI Endpoints` in the main [documentation](README.md).

## ⚗️ Test the created project

  - Run the following command `quarkus dev`.
  - Test the Greetings API: `curl http://localhost:8080/hello`

## 💬 Part one: blocking chat completion 🛑

  - Create the interface [MistralAIService](../../java/chat-bot/src/main/java/com/ovhcloud/workshop/aiendpoints/services/MistralAIService.java) with the following code:
```java
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
```
  - Add the following configuration to the [application.properties](../../java/chat-bot/src/main/resources/application.properties) file:
```java
### AI Endpoints parameters
# Base URL for Mistral AI endpoints
quarkus.langchain4j.mistralai.base-url=https://mistral-7b-instruct-v02.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1
# Activate or not the log during the request
quarkus.langchain4j.mistralai.log-requests=true
# Activate or not the log during the response
quarkus.langchain4j.mistralai.log-responses=true
# Delay before raising a timeout exception                   
quarkus.langchain4j.mistralai.timeout=60s   
# Key is not mandatory for AI Endpoints but the quarkus extension need it
quarkus.langchain4j.mistralai.api-key=foo
# Number of token to use for answer
quarkus.langchain4j.mistralai.chat-model.max-tokens=1500
# Model to use
quarkus.langchain4j.mistralai.chat-model.model-name=Mistral-7B-Instruct-v0.2
```
  - Create the resource endpoint [ChatBotResource](../../java/chat-bot/src/main/java/com/ovhcloud/workshop/aiendpoints/resources/ChatBotResource.java) with the following code:
```java
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
public class ChatBotResource {

  @Inject
  private MistralAIService aiService;

  private static String robot="🤖:\n";

  /**
   * Use the chat in a blocking way (i.e wait that the model send the complete response)
   * 
   * @return The model response.
   */
  @Path("/blocking")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String blocking(@RestQuery("question") String question) {
    return  robot + aiService.ask(question);
  }
}
```
  - Test the chat bot:
    - run `quarkus dev` if you had stopped it
    - test the chat bot: `http://localhost:8080/chatbot/blocking?question=%22Who%20are%20you?%22`

## 💬 Part two: streaming chat completion 🔀

  - update the interface [MistralAIService](../../java/chat-bot/src/main/java/com/ovhcloud/workshop/aiendpoints/services/MistralAIService.java) with the following code:
```java
/**
 * Streaming call: the response is sent bloc by bloc by the model.
 * 
 * @param question The question to ask to the model.
 * @return The model answer.
 */
@SystemMessage("You are a Nestor, a virtual assistant.")
@UserMessage("Answer to the question: {question}.")
Multi<String> askStreaming(String question);
```
  - update the resource endpoint [ChatBotResource](../../java/chat-bot/src/main/java/com/ovhcloud/workshop/aiendpoints/resources/ChatBotResource.java):
```java
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
```
  - Test the chat bot:
    - run `quarkus dev` if you had stopped it
    - test the chat bot: `curl http://localhost:8080/chatbot/streaming?question=%22Can%20you%20explain%20in%20a%20long%20way%20xho%20are%20you?%22`