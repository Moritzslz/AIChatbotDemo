package pnt.ai.chatbotdemo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/chat")
    @CrossOrigin(origins = "http://localhost:8080/chat")
    public String chat() {
        return "chat.html";
    }
}
