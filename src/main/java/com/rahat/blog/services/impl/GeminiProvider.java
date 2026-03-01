package com.rahat.blog.services.impl;

import com.rahat.blog.domain.entities.Tag;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.repositories.TagRepository;
import com.rahat.blog.services.AiProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;


@Component
public class GeminiProvider implements AiProviderService {
    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    String apiKey;
    public GeminiProvider(
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;

    }

    @Override
    public String generate(String prompt) {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/" +
                        "gemini-3-flash-preview:generateContent?key=" + apiKey;



        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);

        assert response.getBody() != null;
        return response.getBody()
                .get("candidates")
                .get(0)
                .get("content")
                .get("parts")
                .get(0)
                .get("text").asString();
    }
}
