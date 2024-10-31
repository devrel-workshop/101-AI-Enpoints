## Java module for AI Endpoints workshop

**ℹ️ All solutions to this part are in the [solution/chatbot](../../solutions/chatbot/) folder. ℹ️**

### ⚠️ Prerequisites ⚠️

If you use [Coder CDE](https://coder.com/) to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Java 21 (LTS)
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)

> Note: most of the modules will use these librairies make the simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus](https://quarkus.io/)

### 🤖 Module 1: Chatbot with AI Endpoints and LangChain4J ☕️

The goal of this module is to develop a simple chat bot with AI Endpoints and Java.  
The exercice is divided in 4 parts:
1. Create a simple chatbot: [SimpleChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/SimpleChatbot.java)
1. Create a streaming chatbot: [StreamingChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/StreamingChatbot.java)
1. Create a memory chatbot: [MemoryChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/MemoryChatbot.java)
1. Create a chatbot with RAG: [RAGChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/RAGChatbot.java)

And at the end assembling all the parts to create a complete chatbot.

🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

⚗️ Test your code by running the following commands: 
 - Simple chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.SimpleChatbot"`
 - Advanced chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.StreamingChatbot"`
 - Memory chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.MemoryChatbot"`
 - RAG chatbot: `mvn clean compile && mvn exec:java -Dexec.mainClass="com.ovhcloud.ai.langchain4j.chatbot.RAGChatbot"`

👩‍💻 How to develop ? 🧑‍💻

>Note: after the first exercice to create a simple chatbot you can use the same class by adding new feature or create a new file for each exercice.
>The solutions are in separate files to facilitate the reading.

  - all needed files are pre-created in [java-langchain4j](./java-langchain4j/) folder
  - the main resources:
    - the [pom.xml](./java-langchain4j/pom.xml) file
    - the [SimpleChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/SimpleChatbot.java) class
    - the [content.txt](./java-langchain4j/src/resources/rag-files/content.txt) file for RAG part
    - the [StreamingChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/StreamingChatbot.java) class
    - the [MemoryChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/MemoryChatbot.java) class
    - the [RAGChatbot](./java-langchain4j/src/main/java/com/ovhcloud/ai/langchain4j/chatbot/RAGChatbot.java) class

### 🤖 Module 2: Chatbot with AI Endpoints and Quarkus ⚡️