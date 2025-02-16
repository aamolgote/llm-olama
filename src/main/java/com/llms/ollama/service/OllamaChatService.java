package com.llms.ollama.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OllamaChatService {
    @Qualifier("ollamaChatModel")
    private final OllamaChatModel ollamaChatModel;

    private static final String PROMPT_INSTRUCTIONS = """
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
        var generalInstructionsSystemMessage = new SystemMessage(PROMPT_INSTRUCTIONS);
        var currentPromptMessage = new UserMessage(message);
        var prompt = new Prompt(List.of(generalInstructionsSystemMessage, currentPromptMessage));
        AssistantMessage assistantMessage = ollamaChatModel.call(prompt).getResult().getOutput();
        return assistantMessage.getText();
    }
}
