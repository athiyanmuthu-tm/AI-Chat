package com.example.aichat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Generates a reply. If OPENAI_API_KEY is set in the environment, this will attempt
     * to call OpenAI's ChatCompletions API (an example) and return the generated text.
     * Otherwise it falls back to a simple dummy implementation.
     */
    public String generateReply(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "Please say something!";
        }

        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey != null && !apiKey.isBlank()) {
            try {
                String aiText = callOpenAi(apiKey, message);
                if (aiText != null) return aiText;
            } catch (Exception e) {
                // If the API call fails, we'll gracefully fallback to dummy replies
                System.err.println("OpenAI call failed: " + e.getMessage());
            }
        }

        // Dummy heuristics
        String lower = message.toLowerCase();
        if (lower.contains("hello") || lower.contains("hi") || lower.contains("hey")) {
            return "Hello! How can I help you today?";
        }
        if (lower.contains("time")) {
            return "I can't access the real time, but if I could I'd tell you the current time!";
        }
        if (lower.contains("joke")) {
            return "Why don't programmers like nature? It has too many bugs!";
        }

        // Default: simple echo
        return "AI Reply: " + message;
    }

    // --- Example call to OpenAI chat completions (commented/optional) ---
    private String callOpenAi(String apiKey, String message) throws IOException, InterruptedException {
        // Example using the new Chat Completions endpoint
        // NOTE: To use this, make sure your environment is configured and the account is valid.
        String url = "https://api.openai.com/v1/chat/completions";
        HttpClient client = HttpClient.newHttpClient();
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        // Build minimal messages array
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", message);
        body.put("messages", new Map[]{userMsg});

        String reqBody = mapper.writeValueAsString(body);

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(reqBody))
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() == 200) {
            JsonNode root = mapper.readTree(resp.body());
            if (root.has("choices") && root.get("choices").isArray() && root.get("choices").size() > 0) {
                JsonNode content = root.get("choices").get(0).path("message").path("content");
                return content.isMissingNode() ? null : content.asText();
            }
        }
        return null;
    }
}
