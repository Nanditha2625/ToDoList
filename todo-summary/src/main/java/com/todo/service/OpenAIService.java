package com.todo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class OpenAIService {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    public String getSummary(String prompt) {
        try {
            WebClient client = WebClient.create("https://api.openai.com/v1/chat/completions");

            // Prepare request body
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-3.5-turbo");
            body.put("temperature", 0.7);
            body.put("messages", List.of(
                    Map.of("role", "user", "content", prompt)
            ));

            // Call OpenAI API and parse response
            Map<String, Object> res = client.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // Extract summary content
            List<Map<String, Object>> choices = (List<Map<String, Object>>) res.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            String content = (String) message.get("content");

            return content.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Summary generation failed.";
        }
    }
}
