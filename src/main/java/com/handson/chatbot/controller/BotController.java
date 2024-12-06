package com.handson.chatbot.controller;

import com.handson.chatbot.service.AmazonService;
import com.handson.chatbot.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/bot")
public class BotController {

    @Autowired
    AmazonService amazonService;

    @Autowired
    JokeService jokeService; // Inject JokeService here

    @RequestMapping(value = "/amazon", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@RequestParam String keyword) throws IOException {
        return new ResponseEntity<>(amazonService.searchProducts(keyword), HttpStatus.OK);
    }

    @RequestMapping(value = "/jokes", method = RequestMethod.GET)
    public ResponseEntity<?> getJokes(@RequestParam String query) {
        try {
            String jokes = jokeService.getJokes(query);
            return new ResponseEntity<>(jokes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error fetching jokes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "", method = {RequestMethod.POST})
    public ResponseEntity<?> getBotResponse(@RequestBody BotQuery query) throws IOException {
        // Extract the parameters from the query result
        Map<String, Object> params = query.getQueryResult().getParameters();
        String res = "Not found";

        // Check for specific parameters and call the respective service

         if (params.containsKey("movie")) {
            res = amazonService.searchProducts((String) params.get("movie"));
        } else if (params.containsKey("subject")) {
            String subject = (String) params.get("subject");
            if (subject.toLowerCase().contains("joke")) {
                res = jokeService.searchJokes("chuck norris");
            }
        }

        // Return the response
        return new ResponseEntity<>(BotResponse.of(res), HttpStatus.OK);
    }

    // Updated BotQuery class
    static class BotQuery {
        QueryResult queryResult;

        public QueryResult getQueryResult() {
            return queryResult;
        }

        public void setQueryResult(QueryResult queryResult) {
            this.queryResult = queryResult;
        }
    }

    // Updated QueryResult class
    static class QueryResult {
        Map<String, Object> parameters; // Handle nested and varied parameter values

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }
    }

    // BotResponse class remains unchanged
    static class BotResponse {
        String fulfillmentText;
        String source = "BOT";

        public String getFulfillmentText() {
            return fulfillmentText;
        }

        public String getSource() {
            return source;
        }

        public static BotResponse of(String fulfillmentText) {
            BotResponse res = new BotResponse();
            res.fulfillmentText = fulfillmentText;
            return res;
        }
    }

}