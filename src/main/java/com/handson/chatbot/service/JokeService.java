package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.chatbot.model.Joke;
import com.handson.chatbot.model.JokeResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class JokeService {
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper om = new ObjectMapper();

    public String getJokes(String query) throws IOException {
        String apiUrl = "https://api.chucknorris.io/jokes/search?query=" + query;

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            return "Error: Unable to fetch jokes (HTTP " + response.code() + ")";
        }

        String jsonResponse = response.body().string();
        JokeResponse jokeResponse = om.readValue(jsonResponse, JokeResponse.class);

        if (jokeResponse.getTotal() == 0) {
            return "No jokes found for query: " + query;
        }

        // Format jokes
        return jokeResponse.getResult().stream()
                .map(joke -> "Joke: " + joke.getValue() + "\nURL: " + joke.getUrl() + "\n")
                .collect(Collectors.joining("\n"));
    }

        public String searchJokes(String keyword) throws IOException {
            // URL for the Chuck Norris joke API
            String apiUrl = "https://api.chucknorris.io/jokes/search?query=" + keyword;

            // Make an HTTP GET request using OkHttp
            Request request = new Request.Builder().url(apiUrl).get().build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);

                // Parse the first joke from the result array
                if (rootNode.has("result") && rootNode.get("result").size() > 0) {
                    return rootNode.get("result").get(0).get("value").asText();
                }
            }

            return "No jokes found.";
        }
    }


