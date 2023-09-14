package pnt.ai.chatbotdemo.Services;

import pnt.ai.chatbotdemo.DTOs.OpenAIRequest;
import pnt.ai.chatbotdemo.DTOs.OpenAIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Value("${openai.apiurl}")
    private String API_URL;
    @Value("${openai.model}")
    private String MODEL;
    @Value("${openai.n}")
    private int N;
    @Value("${openai.temperature}")
    private double TEMPERATURE;
    @Value("${openai.max_tokens}")
    private int MAX_TOKENS;
    @Value("${openai.prompt.system}")
    private String systemPrompt;
    @Autowired
    private RestTemplate restTemplate;

    public OpenAIRequest createRequest() {
        OpenAIRequest request = new OpenAIRequest(MODEL, N, TEMPERATURE, MAX_TOKENS);
        request.addMessage("system", systemPrompt);
        return request;
    }

    public OpenAIResponse getCompletion(OpenAIRequest request) {
        OpenAIResponse response = restTemplate.postForObject(API_URL, request, OpenAIResponse.class);
        return response;
    }
}
