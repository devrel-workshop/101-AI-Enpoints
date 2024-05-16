## 🤖 Steps to do the chat bot exercices 💬

## ⚠️ Prerequisites ⚠️

Before begging, we assume that you have created the `chat-bot` project, see section `Module 1: Chat bot with AI Endpoints` in the main [documentation](README.md).

## ⚗️ Test the created project

  - Run the following command `quarkus dev`.
  - Test the Greetings API: `curl http://localhost:8080/hello`

## 💬 Part one: blocking chat completion 🛑

  - Create the interface [MistralAIService](../../java/chat-bot/src/main/java/com/ovhcloud/workshop/aiendpoints/services/MistralAIService.java)
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
  - Create the resource endpoint [ChatBotResource](../../java/chat-bot/src/main/java/com/ovhcloud/workshop/aiendpoints/resources/ChatBotResource.java)
  - Test the chat bot:
    - run `quarkus dev` if you had stopped it
    - test the chat bot: `http://localhost:8080/chatbot/blocking?question=%22Who%20are%20you?%22`
