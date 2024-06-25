package org.spring.codingStory.bus.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusBasicController {

  @GetMapping("/busapi/bus/busIndex")
  public String bus(Model model) {
    return "busapi/bus/busIndex";
  }

  @GetMapping("/busapi/bus/busIndexPark")
  public String busPark(Model model) {
    return "busapi/bus/busIndexPark";
  }
}
