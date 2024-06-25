package org.spring.codingStory.chatbot.chatbot;

import org.spring.codingStory.chatbot.dto.MessageDTO;
import org.spring.codingStory.chatbot.service.KomoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatbot")
public class ChatBotController {

    @Autowired
    private KomoranService komoranService;

    @GetMapping("/chatIndex")
    public String message(MessageDTO messageDTO) {

        return "chatbot/chatIndex";
    }


    @PostMapping("/botController")
    public String message(String message, Model model) throws Exception {

        model.addAttribute("msg", komoranService.nlpAnalyze(message));

        return "chatbot/bot-message";
    }
}
