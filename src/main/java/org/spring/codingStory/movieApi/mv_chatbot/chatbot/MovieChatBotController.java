package org.spring.codingStory.movieApi.mv_chatbot.chatbot;
import org.spring.codingStory.movieApi.mv_chatbot.dto.MovieMessageDTO;
import org.spring.codingStory.movieApi.mv_chatbot.service.MovieKomoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class MovieChatBotController {

    @Autowired
    private MovieKomoranService movieKomoranService;

    @PostMapping("/chatController")
    public String message(String message, Model model) throws Exception {
        MovieMessageDTO botResponse = movieKomoranService.nlpAnalyze(message);
        model.addAttribute("msg", botResponse);
        return "chat/bot-message";
    }

    @GetMapping("/index")
    public String message() {
        return "chat/index";
    }
}