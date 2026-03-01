package com.rahat.blog.controllers;

import com.rahat.blog.domain.dtos.AiRequestDto;
import com.rahat.blog.services.AiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")

public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/blog")
    public ResponseEntity<Map<String, String>> generate(
            @Valid @RequestBody AiRequestDto body) {
        String result = aiService.generateBlog(body.text(), body.category(), body.tags());
        return ResponseEntity.ok(Map.of("content", result));
    }
}

//sample json request
//{
//    "text": "write the history",
//        "category": "6a18c3b7-0635-4372-bfaf-0b77b33fbcdd",
//        "tags": [
//    "86b1df64-bdf5-497a-b6a3-8b43a06bf4fd",
//            "08c8530a-8fd6-4f35-a19c-86f9ecc02e7c"
//  ]
//}