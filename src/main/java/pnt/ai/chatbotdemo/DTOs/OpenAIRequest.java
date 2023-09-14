package pnt.ai.chatbotdemo.DTOs;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenAIRequest {

    private String model;
    private int n;
    private double temperature;
    private int max_tokens;
    private List<Message> messages;

    public OpenAIRequest(String model,
                         int n,
                         double temperature,
                         int max_tokens) {
        this.model = model;
        this.n = n;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
        this.messages = new ArrayList<>();
    }

    public void addMessage(String role, String prompt) {
        this.messages.add(new Message(role, prompt));
    }
}

