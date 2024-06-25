package org.spring.codingStory.weatherChatbot.chatbot;

import org.spring.codingStory.weatherChatbot.dto.WeatherMessageDTO;
import org.spring.codingStory.weatherChatbot.service.WeatherKomoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/weather") //localhost:8095/board
@Controller
public class WeatherChatBotController {

	@Autowired
	private WeatherKomoranService weatherKomoranService;


	@GetMapping("/index")
	public String message(WeatherMessageDTO weatherMessageDTO) {

		return "weather/index";
	}
	
	@PostMapping("/botController")
	public String message(String message,Model model) throws Exception {
		
		model.addAttribute("msg", weatherKomoranService.nlpAnalyze(message));
		
		return "weather/weatherchatbot/bot-message";
	}
}
