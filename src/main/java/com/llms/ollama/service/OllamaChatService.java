package com.llms.ollama.service;

import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OllamaChatService {
    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatModel;

    private static final String INSTRUCTION_FOR_SYSTEM_PROMPT = """
    We will using you as a agent to generate unit tests for the code that is been passed to you, the code would be primarily in Java.
        
    You will generate the unit test code and return in back.
    
    Please follow the strict guidelines  
    If the code is in Java then only generate the unit tests and return back, else return 'Language not supported answer'     
    If the prompt has any thing else than the Java code provide the answer 'Incorrect input'
    """;

    public OllamaChatService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatModel = ollamaChatClient;
    }

    public String generateUnitTest(String message){
        String responseMessage = null;
        SystemMessage systemMessage = new SystemMessage(INSTRUCTION_FOR_SYSTEM_PROMPT);
        UserMessage userMessage = new UserMessage(message);
        List<Message> messageList = new ArrayList<>();
        messageList.add(systemMessage);
        messageList.add(userMessage);
        Prompt userPrompt = new Prompt(messageList);
        ChatResponse extChatResponse = ollamaChatModel.call(userPrompt);
        if (extChatResponse != null && extChatResponse.getResult() != null
            && extChatResponse.getResult().getOutput() != null){
            AssistantMessage assistantMessage = ollamaChatModel.call(userPrompt).getResult().getOutput();
            responseMessage = assistantMessage.getText();
        }
        return responseMessage;
    }
}
