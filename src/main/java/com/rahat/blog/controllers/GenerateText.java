package com.rahat.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/text")
public class GenerateText {

    private final RestTemplate restTemplate;
    private final JavaMailSender mailSender;
    public GenerateText(RestTemplate restTemplate, JavaMailSender mailSender) {
        this.restTemplate = restTemplate;
        this.mailSender = mailSender;
    }

    @PostMapping("/generate")
    public String generate() {
        String url = "http://localhost:8000/generate";

        String prompt = "You are";

        Map<String, Object> request = new HashMap<>();
        request.put("prompt", prompt);
//        request.put("max_length", 50); // can adjust

        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);

        if (response == null || response.get("generated_text") == null) {
            throw new RuntimeException("Text generation failed");
        }

        String body = response.get("generated_text").toString();
        String email = "abu.hasnath@gigatechltd.com";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Ai text to make your day");
        message.setText(body);
        mailSender.send(message);
        return response.get("generated_text").toString();
    }
}


