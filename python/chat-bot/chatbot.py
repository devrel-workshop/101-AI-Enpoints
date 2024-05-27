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
                    "system",
                    "You are a Nestor, a virtual assistant. Answer to the question.",
                ),
                ("human", "{user_input}"),
            ]
        )

        chain = prompt | model

        response = chain.invoke(new_message)

        return response.content

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
    os.environ["GRADIO_ANALYTICS_ENABLED"] = str(False)
    parser = argparse.ArgumentParser()
    parser.add_argument("--port", default=8000)
    parser.add_argument("--host", default="0.0.0.0")
    parser.add_argument("--path", default="/app")
    parser.add_argument("--api", default="http://localhost:8001")
    parser.add_argument("--temperature", default=1.0)
    parser.add_argument("--model", default=None)
    args = parser.parse_args()

    # Create the chat model, no need to use a token
    model = ChatMistralAI(
        model="Mixtral-8x22B-Instruct-v0.1",
        api_key="None",
        endpoint=f"{args.api}/api/openai_compat/v1",
        max_tokens=1500,
    )

    # Create gradio app
    ui = chat_interface(model)
    fastAPIApp = FastAPI()
    gradioApp = gr.mount_gradio_app(fastAPIApp, ui, path=args.path)

    # Run web server
    print(f"Starting gradio on {args.host}:{args.port}{args.path}")
    uvicorn.run(
        gradioApp, host=args.host, port=args.port, log_level="debug", access_log=True
    )


if __name__ == "__main__":
    main()
