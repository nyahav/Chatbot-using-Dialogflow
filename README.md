# 🤖 ChatBot Application 🚀

**Transforming Interaction with Intelligent Responses**  
_A modern chatbot with integrated APIs, movies and jokes search, and interactive documentation._  

---

## 🌟 Features

### 🤝 Google Cloud Dialogflow Integration
- **Natural language understanding** powered by GCP Dialogflow.
- Route queries intelligently using intent and parameter mapping.
- Responds to user requests via a custom webhook.

- ### 🛠️ Interactive Swagger UI
- Full API documentation for seamless testing and exploration.
- **URL**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 🔍 Movie Search via IMDB
- Easily search for movies with **IMDB's catalog** integration.
- Returns movie name, rating, and category.

### 🎭 Chuck Norris Jokes
- Lighten up your day with hilarious jokes from the **Chuck Norris API**.


---

## 🏗️ Project Structure

```plaintext
src/
├── config/SwaggerConfig.java          # Swagger API configuration
├── service/
│   ├── AmazonService.java             # Handles Amazon product search
│   ├── WeatherService.java            # Fetches weather data
│   ├── JokeService.java               # Fetches Chuck Norris jokes
├── controller/BotController.java      # Main bot controller and webhook handler
├── resources/application.properties   # Configuration file
└── webhook/
    ├── BotQuery.java                  # Maps Dialogflow's webhook request payload
    ├── BotResponse.java               # Formats responses for Dialogflow
    └── WebhookHandler.java            # Handles incoming webhook requests

⚙️ Setup Instructions
Prerequisites

    Java 11+
    Maven
    API keys for weather and Amazon if applicable (optional).
Setting Up Dialogflow

    Create a GCP Project:
        Enable the Dialogflow API in Google Cloud Console.

    Build Intents:
        Define intents (e.g., WeatherSearch, ProductSearch, GetJokes) in Dialogflow with the required training phrases.

    Configure Parameters:
        Map user inputs like city, product, or query to Dialogflow parameters.

    Webhook Setup:
        In Dialogflow's Fulfillment tab, enable webhook and set the URL to:

        https://<your-host-url>/bot

        Example: https://webhook.site/your-webhook-path

Webhook Handler

The BotController.java is responsible for processing Dialogflow webhook requests:

    Input: Handles city, product, or query parameters.
    Output: Generates a response based on the detected intent and mapped service.

Steps

    Clone the repository:

git clone https://github.com/your-repo/chatbot.git
cd chatbot

Build the project:

mvn clean install

Run the application:

    mvn spring-boot:run

    Access Swagger at:
    http://localhost:8080/swagger-ui.html

🔧 Dependencies
📜 pom.xml Highlights

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.6.1</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.6.1</version>
</dependency>
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.8.1</version>
</dependency>

🌈 Key Highlights

    Interactive APIs for real-world application scenarios.
    Fully configurable Swagger UI for live testing.
    Integrated with advanced Java libraries like OkHttp for HTTP calls.

📝 Sample API Usage
🌐 Product Search Endpoint
URL: /bot/amazon

GET /bot/amazon?keyword=iphone

🌤️ Weather Search Endpoint
URL: /bot/weather

POST /bot
{
  "queryResult": {
    "parameters": {
      "city": "New York"
    }
  }
}

🎭 Joke Fetching Endpoint
URL: /bot/jokes

GET /bot/jokes?query=funny

🛡️ Future Improvements

    Add authentication for secure access.
    Expand API integrations for broader functionality.
    Improve UI/UX for a frontend chatbot.

🎉 Thank you for exploring the ChatBot Application!
