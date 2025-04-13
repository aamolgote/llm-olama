package com.llms.ollama.controller;

import com.llms.ollama.model.ChatRequest;
import com.llms.ollama.model.ChatResponse;
import com.llms.ollama.service.OllamaChatService;
import com.llms.ollama.service.OllamaContentFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai/ollama")
public class OllamaChatController {

    @Autowired
    OllamaChatService ollamaChatService;

    @Autowired
    OllamaContentFeedback ollamaContentFeedback;

    @PostMapping("/unit-test")
    public ChatResponse generateUnitTests(@RequestBody ChatRequest request) {
        String response = this.ollamaChatService.generateUnitTest(request.getPrompt());
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setMessage(response);
        return chatResponse;
    }

    @PostMapping("/content/feedback")
    public ChatResponse generateFeedbackForBookContent(@RequestBody ChatRequest request) {
        String response = this.ollamaContentFeedback.generateFeedback(request.getPrompt());
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setMessage(response);
        return chatResponse;
    }
}