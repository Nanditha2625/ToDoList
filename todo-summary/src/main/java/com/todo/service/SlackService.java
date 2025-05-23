package com.todo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SlackService {

    @Value("${SLACK_WEBHOOK_URL}")
    private String webhook;

    public boolean sendMessage(String message) {
        try {
            WebClient.create()
                    .post()
                    .uri(webhook)
                    .header("Content-Type", "application/json")
                    .bodyValue("{\"text\":\"" + message + "\"}")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return true;
        } catch (Exception e) {
            e.printStackTrace();  // For debugging
            return false;
        }
    }
}
