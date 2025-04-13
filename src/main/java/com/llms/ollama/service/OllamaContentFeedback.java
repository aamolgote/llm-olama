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
public class OllamaContentFeedback {
    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatModel;

    private static final String INSTRUCTION_FOR_SYSTEM_PROMPT = """
    We will using you as a agent to to provide the feedback for the book content provided to you, you will be provided with book section.
    For the section you need to provide summary and then finally provide the improvements that can be made to the section. We have to focus on the feedback for the content quality and not on the english or sentences.
    Please ignore grammar errors and typos. What you see is a raw manuscript; it has not been copy edited.
    """;

    public OllamaContentFeedback(OllamaChatModel ollamaChatClient) {
        this.ollamaChatModel = ollamaChatClient;
    }

    public String generateFeedback(String message){
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
