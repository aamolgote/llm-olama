package com.llms.ollama.controller;

import com.llms.ollama.model.ChatRequest;
import com.llms.ollama.model.ChatResponse;
import com.llms.ollama.service.OllamaChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/ollama")
public class OllamaChatController {

    @Autowired
    OllamaChatService ollamaChatService;

    @PostMapping("/unit-test")
    public ChatResponse generateUnitTests(@RequestBody ChatRequest request) {
        String response = this.ollamaChatService.generateUnitTest(request.getPrompt());
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setMessage(response);
        return chatResponse;
    }
}