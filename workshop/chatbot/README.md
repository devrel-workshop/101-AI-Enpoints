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
1. Create a blocking chatbot
1. Create a streaming chatbot
1. Create a memory chatbot
1. Create a chatbot with RAG

And at the end assembling all the parts to create a complete chatbot.

🔗 Useful resources:
 - [LangChain4j](https://docs.langchain4j.dev/get-started)
 - [MistralAI integration](https://docs.langchain4j.dev/integrations/language-models/mistral-ai) in LangChain4j

⚗️ Test your code by running the following scripts:
1. blocking chatbot: 
1. streaming chatbot
1. memory chatbot: [04-chatbot-memory.sh](./java-langchain4j/04-chatbot-memory.sh)
1. chatbot with RAG: [05-chatbot-rag.sh](./java-langchain4j/05-chatbot-rag.sh)

### 🤖 Module 2: Chatbot with AI Endpoints and Quarkus ⚡️