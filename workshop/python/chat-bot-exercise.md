## 🤖 Steps to do the chat bot exercises 💬

## ⚠️ Prerequisites ⚠️

Before begging, we assume that you have created the `chat-bot` project, see section `Module 1: Chat bot with AI Endpoints` in the main [documentation](README.md).  
You can find all solutions in the [python/chat-bot](../../python/chat-bot) folder.

## 📦 Install the dependencies

  - Create a virtual environment: `python3 -m venv ./venv`
  - Install the dependencies: `pip3 install -r python/chat-bot/requirements.txt` with the following content:
```python
fastapi
langchain
langchain-mistralai
langchain_community
langchain_chroma
argparse
unstructured
langchainhub
```

## 💬 Part one: blocking chat completion 🛑

  - Create the script [chatbot.py](../../python/chat-bot/chatbot-blocking.py) with the following code:
```python
import gradio as gr
import uvicorn
import argparse
import os

from fastapi import FastAPI

from langchain_mistralai import ChatMistralAI
from langchain_core.prompts import ChatPromptTemplate


# Factory to create the callback function for the chat interface
def callback_factory(model: ChatMistralAI):
    # Function to call the LLM model
    # new-message: User message to send to the model
    # context: Context of the conversation (history, ...)
    def chat_completion(new_message: str, context: str):
        prompt = ChatPromptTemplate.from_messages(
            [
                (
                    'system',
                    'You are a Nestor, a virtual assistant. Answer to the question.',
                ),
                ('human', '{user_input}'),
            ]
        )

        chain = prompt | model

        return chain.invoke(new_message).content

    return chat_completion


# Chat UI for human interaction
def chat_interface(model):
    callback_function = callback_factory(model)

    ui = gr.ChatInterface(
        callback_function, fill_height=True, autofocus=False, concurrency_limit=None
    )

    return ui


# Main entrypoint
def main():
    # Get parameter from command line, for example python3 chatbot.py --port 8080
    os.environ['GRADIO_ANALYTICS_ENABLED'] = str(False)
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', default=8000)
    parser.add_argument('--host', default='0.0.0.0')
    parser.add_argument('--path', default='/app')
    parser.add_argument('--api', default='http://localhost:8001')
    parser.add_argument('--temperature', default=1.0)
    parser.add_argument('--model', default=None)
    args = parser.parse_args()

    # Create the chat model, no need to use a token
    model = ChatMistralAI(
        model='Mixtral-8x22B-Instruct-v0.1',
        api_key='None',
        endpoint=f'{args.api}/api/openai_compat/v1',
        max_tokens=1500,
    )

    # Create gradio app
    ui = chat_interface(model)
    fastAPIApp = FastAPI()
    gradioApp = gr.mount_gradio_app(fastAPIApp, ui, path=args.path)

    # Run web server
    print(f'Starting gradio on {args.host}:{args.port}{args.path}')
    uvicorn.run(
        gradioApp, host=args.host, port=args.port, log_level='debug', access_log=True
    )


if __name__ == '__main__':
    main()
```
  - run `python3 chatbot-blocking.py --api https://mixtral-8x22b-instruct-v01.endpoints.kepler.ai.cloud.ovh.net --model Mixtral-8x22B-Instruct-v0.1`
  - Test the chat bot with a browser: `http://http://0.0.0.0:8000/app/`

## 💬 Part two: streaming chat completion 🔀

  - create [chatbot-streaming.py](../../python/chat-bot/chatbot-streaming.py) to use the streaming API:
```python
import gradio as gr
import uvicorn
import argparse
import os
import time

from fastapi import FastAPI

from langchain_mistralai import ChatMistralAI
from langchain_core.prompts import ChatPromptTemplate


# Factory to create the callback function for the chat interface
def callback_factory(model: ChatMistralAI):
    # Function to call the LLM model
    # new-message: User message to send to the model
    # context: Context of the conversation (history, ...)
    def chat_completion(new_message: str, context: str):
        prompt = ChatPromptTemplate.from_messages(
            [
                (
                    'system',
                    'You are a Nestor, a virtual assistant. Answer to the question.',
                ),
                ('human', '{user_input}'),
            ]
        )

        chain = prompt | model

        response = ""
        for r in chain.stream({"user_input", new_message}):
            time.sleep(0.150)
            response = response + r.content
            yield response

    return chat_completion


# Chat UI for human interaction
def chat_interface(model):
    callback_function = callback_factory(model)

    ui = gr.ChatInterface(
        callback_function, fill_height=True, autofocus=False, concurrency_limit=None
    )

    return ui


# Main entrypoint
def main():
    # Get parameter from command line, for example python3 chatbot.py --port 8080
    os.environ['GRADIO_ANALYTICS_ENABLED'] = str(False)
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', default=8000)
    parser.add_argument('--host', default='0.0.0.0')
    parser.add_argument('--path', default='/app')
    parser.add_argument('--api', default='http://localhost:8001')
    parser.add_argument('--temperature', default=1.0)
    parser.add_argument('--model', default=None)
    args = parser.parse_args()

    # Create the chat model, no need to use a token
    model = ChatMistralAI(
        model='Mixtral-8x22B-Instruct-v0.1',
        api_key='None',
        endpoint=f'{args.api}/api/openai_compat/v1',
        max_tokens=1500,
    )

    # Create gradio app
    ui = chat_interface(model)
    fastAPIApp = FastAPI()
    gradioApp = gr.mount_gradio_app(fastAPIApp, ui, path=args.path)

    # Run web server
    print(f'Starting gradio on {args.host}:{args.port}{args.path}')
    uvicorn.run(
        gradioApp, host=args.host, port=args.port, log_level='debug', access_log=True
    )


if __name__ == '__main__':
    main()
```
  - run `python3 chatbot-streaming.py --api https://mixtral-8x22b-instruct-v01.endpoints.kepler.ai.cloud.ovh.net --model Mixtral-8x22B-Instruct-v0.1`
  - Test the chat bot with a browser: `http://http://0.0.0.0:8000/app/`

## 💬 Part three: RAG 🗃️

 - add the file [content.txt](../../python/chat-bot/rag-files/content.txt) in a folder named `rag-files`
 - create the script [chatbot-rag.py](../../python/chat-bot/chatbot-rag.py) with the following code:
```python
import gradio as gr
import uvicorn
import argparse
import os
import time

from fastapi import FastAPI

from langchain_mistralai import ChatMistralAI
from langchain_core.prompts import ChatPromptTemplate

from langchain import hub

from langchain_mistralai import ChatMistralAI

from langchain_chroma import Chroma

from langchain_community.document_loaders import DirectoryLoader
from langchain_community.embeddings.ovhcloud import OVHCloudEmbeddings

from langchain_core.runnables import RunnablePassthrough

from langchain_text_splitters import RecursiveCharacterTextSplitter

# Factory to create the callback function for the chat interface
def callback_factory(model: ChatMistralAI):
    # Function to call the LLM model
    # new-message: User message to send to the model
    # context: Context of the conversation (history, ...)
    def chat_completion(new_message: str, context: str):
        # Load documents from a local directory
        loader = DirectoryLoader(
            glob="**/*",
            path="./rag-files/",
            show_progress=True
        )
        docs = loader.load()

        # Split documents into chunks and vectorize them
        text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
        splits = text_splitter.split_documents(docs)
        vectorstore = Chroma.from_documents(documents=splits, embedding=OVHCloudEmbeddings(model_name="multilingual-e5-base"))

        prompt = hub.pull("rlm/rag-prompt")

        rag_chain = (
            {"context": vectorstore.as_retriever(), "question": RunnablePassthrough()}
            | prompt
            | model
        )

        response = ""
        for r in rag_chain.stream({"question", new_message}):
            time.sleep(0.150)
            response = response + r.content
            yield response

    return chat_completion


# Chat UI for human interaction
def chat_interface(model):
    callback_function = callback_factory(model)

    ui = gr.ChatInterface(
        callback_function, fill_height=True, autofocus=False, concurrency_limit=None
    )

    return ui


# Main entrypoint
def main():
    # Get parameter from command line, for example python3 chatbot.py --port 8080
    os.environ['GRADIO_ANALYTICS_ENABLED'] = str(False)
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', default=8000)
    parser.add_argument('--host', default='0.0.0.0')
    parser.add_argument('--path', default='/app')
    parser.add_argument('--api', default='http://localhost:8001')
    parser.add_argument('--temperature', default=1.0)
    parser.add_argument('--model', default=None)
    args = parser.parse_args()

    # Create the chat model, no need to use a token
    model = ChatMistralAI(
        model='Mixtral-8x22B-Instruct-v0.1',
        api_key='None',
        endpoint=f'{args.api}/api/openai_compat/v1',
        max_tokens=1500,
    )

    # Create gradio app
    ui = chat_interface(model)
    fastAPIApp = FastAPI()
    gradioApp = gr.mount_gradio_app(fastAPIApp, ui, path=args.path)

    # Run web server
    print(f'Starting gradio on {args.host}:{args.port}{args.path}')
    uvicorn.run(
        gradioApp, host=args.host, port=args.port, log_level='debug', access_log=True
    )


if __name__ == '__main__':
    main()
```
  - run `python3 chatbot.py --api https://mixtral-8x22b-instruct-v01.endpoints.kepler.ai.cloud.ovh.net --model Mixtral-8x22B-Instruct-v0.1`
  - Test the chat bot with a browser: `http://http://0.0.0.0:8000/app/`

## 💬 Part three: RAG 🗃️

 - add the file [content.txt](../../python/chat-bot/rag-files/content.txt) in a folder named `rag-files`
 - update the code as following:
```python
import gradio as gr
import uvicorn
import argparse
import os
import time

from fastapi import FastAPI

from langchain_mistralai import ChatMistralAI
from langchain_core.prompts import ChatPromptTemplate

from langchain import hub

from langchain_mistralai import ChatMistralAI

from langchain_chroma import Chroma

from langchain_community.document_loaders import DirectoryLoader
from langchain_community.embeddings.ovhcloud import OVHCloudEmbeddings

from langchain_core.runnables import RunnablePassthrough

from langchain_text_splitters import RecursiveCharacterTextSplitter

# Factory to create the callback function for the chat interface
def callback_factory(model: ChatMistralAI):
    # Function to call the LLM model
    # new-message: User message to send to the model
    # context: Context of the conversation (history, ...)
    def chat_completion(new_message: str, context: str):
        prompt = ChatPromptTemplate.from_messages(
            [
                (
                    'system',
                    'You are a Nestor, a virtual assistant. Answer to the question.',
                ),
                ('human', '{user_input}'),
            ]
        )

        #chain = prompt | model

        # Load documents from a local directory
        loader = DirectoryLoader(
            glob="**/*",
            path="./rag-files/",
            show_progress=True
        )
        docs = loader.load()

        # Split documents into chunks and vectorize them
        text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
        splits = text_splitter.split_documents(docs)
        vectorstore = Chroma.from_documents(documents=splits, embedding=OVHCloudEmbeddings(model_name="multilingual-e5-base"))

        prompt = hub.pull("rlm/rag-prompt")

        rag_chain = (
            {"context": vectorstore.as_retriever(), "question": RunnablePassthrough()}
            | prompt
            | model
        )

        response = ""
        for r in rag_chain.stream({"question", new_message}):
            time.sleep(0.150)
            response = response + r.content
            yield response

    return chat_completion


# Chat UI for human interaction
def chat_interface(model):
    callback_function = callback_factory(model)

    ui = gr.ChatInterface(
        callback_function, fill_height=True, autofocus=False, concurrency_limit=None
    )

    return ui


# Main entrypoint
def main():
    # Get parameter from command line, for example python3 chatbot.py --port 8080
    os.environ['GRADIO_ANALYTICS_ENABLED'] = str(False)
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', default=8000)
    parser.add_argument('--host', default='0.0.0.0')
    parser.add_argument('--path', default='/app')
    parser.add_argument('--api', default='http://localhost:8001')
    parser.add_argument('--temperature', default=1.0)
    parser.add_argument('--model', default=None)
    args = parser.parse_args()

    # Create the chat model, no need to use a token
    model = ChatMistralAI(
        model='Mixtral-8x22B-Instruct-v0.1',
        api_key='None',
        endpoint=f'{args.api}/api/openai_compat/v1',
        max_tokens=1500,
    )

    # Create gradio app
    ui = chat_interface(model)
    fastAPIApp = FastAPI()
    gradioApp = gr.mount_gradio_app(fastAPIApp, ui, path=args.path)

    # Run web server
    print(f'Starting gradio on {args.host}:{args.port}{args.path}')
    uvicorn.run(
        gradioApp, host=args.host, port=args.port, log_level='debug', access_log=True
    )


if __name__ == '__main__':
    main()
```

 - run `python3 chatbot-rag.py --api https://mixtral-8x22b-instruct-v01.endpoints.kepler.ai.cloud.ovh.net --model Mixtral-8x22B-Instruct-v0.1`
 - Test the chat bot with a browser: `http://http://0.0.0.0:8000/app/`