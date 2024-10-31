package com.ovhcloud.ai.langchain4j.chatbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here is the place where you will add the code to create an advanced chatbot.
 * The steps to create your chatbot are :
 * - choose the right model on AI Endpoints
 * (https://endpoints.ai.cloud.ovh.net), we want to use Mistral 7B Instruct
 * - use the LangChain4J wrapper / lib to do the call to the model (use the AI
 * Service style), ⚠️ don't forget your pom.xml ⚠️
 * - Use the streaming option, see
 * https://docs.langchain4j.dev/tutorials/response-streaming
 * - Save the context in memory, see
 * https://docs.langchain4j.dev/tutorials/chat-memory
 * - Add RAG feature, see https://docs.langchain4j.dev/tutorials/rag
 * - add parameters to create a virtual assistant named Nestor
 * - ask two questions to test the chatbot memory and display the answer in a
 * streaming way
 */
public class RAGChatbot {
  private static final Logger _LOG = LoggerFactory.getLogger(RAGChatbot.class);

  // AI Service to create, see https://docs.langchain4j.dev/tutorials/ai-services

  public static void main(String[] args) {
    // Select the Mistral model to use (the streaming one)

    // Create the memory store "in memory"

    // Load the document and split it into chunks

    // Do the embeddings with AI Endpoint model
    // (https://docs.langchain4j.dev/integrations/embedding-models/ovh-ai) and store
    // them in a in memory embedding store

    // Store the vectors in the in memory store, see
    // https://docs.langchain4j.dev/integrations/embedding-stores/in-memory

    // Build the chatbot thanks to the AIService builder
    // The chatbot must be in streaming mode with memory and RAC activated with the
    // previous contentRetriever

    // Send a prompt
  }
}
