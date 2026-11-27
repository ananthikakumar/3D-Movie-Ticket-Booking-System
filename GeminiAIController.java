
package com.cineverse.cineverse.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineverse.cineverse.service.GeminiAIService;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class GeminiAIController {

    private final GeminiAIService geminiAIService;

    public GeminiAIController(GeminiAIService geminiAIService) {
        this.geminiAIService = geminiAIService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(
            @RequestBody Map<String, String> request) {

        try {
            String message = request.get("message");

            if (message == null || message.isBlank()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Message cannot be empty"));
            }

            String reply = geminiAIService.chat(message);

            return ResponseEntity.ok(Map.of("reply", reply));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage() == null
                            ? "Unknown error" : e.getMessage()));
        }
    }
}
