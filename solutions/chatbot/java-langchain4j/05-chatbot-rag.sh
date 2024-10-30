#!/bin/bash

bat -P -r 10: $(basename "$0")

# Set some environment variables to use AI Endpoints
source ../.env

LOCAL_CP=./target/classes:$M2_HOME/repository/dev/langchain4j/langchain4j/0.33.0/langchain4j-0.33.0.jar:$M2_HOME/repository/dev/langchain4j/langchain4j-core/0.33.0/langchain4j-core-0.33.0.jar:$M2_HOME/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar:$M2_HOME/repository/org/apache/opennlp/opennlp-tools/1.9.4/opennlp-tools-1.9.4.jar:$M2_HOME/repository/org/jsoup/jsoup/1.16.1/jsoup-1.16.1.jar:$M2_HOME/repository/org/slf4j/slf4j-api/2.0.7/slf4j-api-2.0.7.jar:$M2_HOME/repository/dev/langchain4j/langchain4j-mistral-ai/0.33.0/langchain4j-mistral-ai-0.33.0.jar:$M2_HOME/repository/com/squareup/retrofit2/retrofit/2.9.0/retrofit-2.9.0.jar:$M2_HOME/repository/com/squareup/retrofit2/converter-jackson/2.9.0/converter-jackson-2.9.0.jar:$M2_HOME/repository/com/fasterxml/jackson/core/jackson-databind/2.16.1/jackson-databind-2.16.1.jar:$M2_HOME/repository/com/fasterxml/jackson/core/jackson-annotations/2.16.1/jackson-annotations-2.16.1.jar:$M2_HOME/repository/com/fasterxml/jackson/core/jackson-core/2.16.1/jackson-core-2.16.1.jar:$M2_HOME/repository/com/squareup/okhttp3/okhttp/4.12.0/okhttp-4.12.0.jar:$M2_HOME/repository/com/squareup/okio/okio/3.6.0/okio-3.6.0.jar:$M2_HOME/repository/com/squareup/okio/okio-jvm/3.6.0/okio-jvm-3.6.0.jar:$M2_HOME/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.9.10/kotlin-stdlib-common-1.9.10.jar:$M2_HOME/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.8.21/kotlin-stdlib-jdk8-1.8.21.jar:$M2_HOME/repository/org/jetbrains/kotlin/kotlin-stdlib/1.8.21/kotlin-stdlib-1.8.21.jar:$M2_HOME/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:$M2_HOME/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.8.21/kotlin-stdlib-jdk7-1.8.21.jar:$M2_HOME/repository/com/squareup/okhttp3/okhttp-sse/4.12.0/okhttp-sse-4.12.0.jar:$M2_HOME/repository/dev/langchain4j/langchain4j-ovh-ai/0.33.0/langchain4j-ovh-ai-0.33.0.jar:$M2_HOME/repository/ch/qos/logback/logback-classic/1.5.6/logback-classic-1.5.6.jar:$M2_HOME/repository/ch/qos/logback/logback-core/1.5.6/logback-core-1.5.6.jar:$M2_HOME/repository/fr/wilda/ai/java-ai-toolkit/1.0.0/java-ai-toolkit-1.0.0.jar

# Compile before launching
mvn clean compile
clear

# Call the chatbot with "Can you explain me what is AI Endpoints?"
java  -classpath $LOCAL_CP fr.wilda.ai.chatbot.RAGStreamingChatbot
