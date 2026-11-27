
package com.cineverse.cineverse.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class GeminiAIService {

    private final RestClient restClient;

    public GeminiAIService() {
        this.restClient = RestClient.create(
                "https://generativelanguage.googleapis.com"
        );
    }

    public String chat(String message) {

        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("Gemini API key not found");
        }

        Map<String, Object> request = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", message)
                                )
                        )
                )
        );

        JsonNode response = restClient.post()
                .uri("/v1beta/models/gemini-2.5-flash:generateContent")
                .header("x-goog-api-key", apiKey)
                .body(request)
                .retrieve()
                .body(JsonNode.class);

        if (response == null) {
            throw new RuntimeException("Empty Gemini response");
        }

        String answer = response
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text")
                .asText();

        if (answer.isBlank()) {
            throw new RuntimeException("Gemini returned no text");
        }

        return answer;
    }
}
