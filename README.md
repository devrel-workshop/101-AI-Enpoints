## Welcome to the 101 workshop source to discover AI Endpoints.

## 🔗 Resources 🔗
 - https://endpoints.ai.cloud.ovh.net
 - https://blog.ovhcloud.com/tag/ai-endpoints/

## 🏁 Getting started 🏁

To use this workshop you just have to clone this repository.

### 🧑‍💻 Note on the use of [Coder](https://coder.com/) CDE ☁️

To help people with their IDE configuration we use Coder during the workshop, this is not a mandatory step but it will help you to have a better experience.  
Ask to have the URL where you can start a Coder workspace giving the AI workshop template.  
You can open your VSCode instance in your browser, choose _code-server_ button or with a ssh tunnel on your local VSCode, choose _VS Code Desktop_.

### ⚡️ Workshop initialisation ⚡️

Before starting the workshop you have to create a `.env` file.
To do this use the `.env.example` file as a template.
Once the file is created, you have to fill it with your values:
  - OVH_AI_ENDPOINTS_ACCESS_TOKEN: go to _My Token_ section on https://endpoints.ai.cloud.ovh.net ans copy the generated token
  - OVH_AI_ENDPOINTS_MODEL_URL: set `https://mistral-7b-instruct-v02.endpoints.kepler.ai.cloud.ovh.net/api/openai_compat/v1`
  - OVH_AI_ENDPOINTS_MODEL_NAME: set `Mistral-7B-Instruct-v0.2`
> You can see all available models here: https://endpoints.ai.cloud.ovh.net/catalog

Execute the script [`_setup.sh`](./_setup.sh) to setup your environment.

## 🗃️ Folder organisation 🗃️

**⚠️ Your location to create your own files to do the workshop is [workshop](./workshop/) ⚠️**
> You can also, just, run the workshop solution in the dedicated folders [java](./java/), [python](./python/), [go](./go/) and [javascript](./js/).

### ☕️ Java module ☕️

In the [workshop/java](./workshop/java/) you will find the Java exercices.
See dedicated [documentation](./workshop/java/README.md) for more information.

### 🐍 Python module 🐍

In the [workshop/python](./workshop/python/) you will find the Python exercices.
See dedicated [documentation](./workshop/python/README.md) for more information.

### 🦫 Go module 🦫

In the [workshop/go](./workshop/go/) you will find the Go exercices.
See dedicated [documentation](./workshop/go/README.md) for more information.

### 🕸️ Javascript module 🕸️

In the [workshop/javascript](./workshop/js/) you will find the Javascript exercices.
See dedicated [documentation](./workshop/js/README.md) for more information.
