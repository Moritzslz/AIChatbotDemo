package pnt.ai.chatbotdemo.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pnt.ai.chatbotdemo.DTOs.Message;
import pnt.ai.chatbotdemo.DTOs.OpenAIRequest;
import pnt.ai.chatbotdemo.DTOs.OpenAIResponse;
import pnt.ai.chatbotdemo.Services.JsonExtractionService;
import pnt.ai.chatbotdemo.Services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/chat")
public class ChatController {

    @Autowired
    private OpenAIService openAIService;
    @Autowired
    private JsonExtractionService jsonExtractionService;
    private ArrayList<Message> messages = new ArrayList<>();

    @GetMapping(path = "/home")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping(path = "/send")
    @CrossOrigin(origins = "http://localhost:8080/chat")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody String userMessage, HttpSession session) {
        String message = jsonExtractionService.extractJsonObject(userMessage, "message").get("message");
        System.out.println("Received message: " + message);
        OpenAIRequest openAIRequest = addMessage(session, "user", message);
        System.out.println("Request Object: " + openAIRequest.getMessages().get(0).getContent());

        // Get completion
        OpenAIResponse response = openAIService.getCompletion(openAIRequest);
        String responseContent = response.getChoices().get(0).getMessage().getContent();
        System.out.println("Generated response: " + responseContent);

        // Add response to request object
        OpenAIRequest nOpenAIRequest = addMessage(session, "assistant", responseContent);
        System.out.println("New Request Object: " + nOpenAIRequest.getMessages().get(0).getContent());

        // Create response object
        Map<String, String> responseBody = new HashMap<>();
        ResponseEntity responseEntity = ResponseEntity.ok(responseBody);
        responseEntity.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseEntity.getHeaders().add("Content-Type", "application/json");
        responseBody.put("assistantMessage", responseContent);

        return ResponseEntity.ok(responseBody);
    }


    // TODO Sessions
    private OpenAIRequest addMessage(HttpSession session, String role, String userMessage) {
        Object openAIRequestObject;
        if (session.getAttribute("RequestObj") == null) {
            openAIRequestObject = openAIService.createRequest();
        } else {
            openAIRequestObject = session.getAttribute("RequestObj");
        }
        OpenAIRequest openAIRequest = (OpenAIRequest) openAIRequestObject;
        openAIRequest.addMessage(role, userMessage);
        session.setAttribute("RequestObj", openAIRequest);
        return openAIRequest;
    }
}
