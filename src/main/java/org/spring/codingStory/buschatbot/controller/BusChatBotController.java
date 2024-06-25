package org.spring.codingStory.buschatbot.controller;

import org.spring.codingStory.bus.service.BusService;
import org.spring.codingStory.buschatbot.service.BusKomoranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BusChatBotController {

  @Autowired
  private BusKomoranService komoranService;
  @Autowired
  private BusService busService;

  @PostMapping("/busChatBot")
  public String message(String message, Model model) throws Exception {


    model.addAttribute("msg", komoranService.nlpAnalyze(message));


    return "buschatbot/bot-message";
  }

  @GetMapping("/buschatbot/chat")
  public String chat(){


    return "buschatbot/chat";
  }






}
