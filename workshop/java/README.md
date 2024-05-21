## Java module for AI Endpoints lab

### ⚠️ Prerequisites ⚠️

If you use GitPod to open this project, all prerequisites are managed for you.  
In the other case you need : 
 - Java 21 (LTS)
 - [Maven 3.9.x](https://maven.apache.org/download.cgi)
 - [Quarkus CLI](https://quarkus.io/guides/cli-tooling)

> Note: most of the modules will use these librairies make the simpler: 
>  - [LangChain4j](https://docs.langchain4j.dev/intro/)
>  - [Quarkus](https://quarkus.io/)

### 🤖 Module 1: Chat bot with AI Endpoints 💬

The goal of this module is to develop a simple chat bot with AI Endpoints and Java.  
To simplify some actions (REST request / exposition for example), we'll use [Quarkus](https://quarkus.io/) as main framework for the project.
To create a Quarkus project run the following command in the `/workshop/java` folder:
```bash
quarkus create app com.ovhcloud.workshop.aiendpoints:chat-bot \
    --extension='quarkus-langchain4j-mistral-ai,rest'
```
Then, go to folder [java/chat-bot/](../../java/chat-bot) and refer to the dedicated [documentation](./chat-bot-exercice.md) for more information. 
