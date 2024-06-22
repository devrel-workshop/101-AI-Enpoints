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
