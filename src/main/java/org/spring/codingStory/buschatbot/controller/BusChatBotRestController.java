package org.spring.codingStory.buschatbot.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.bus.ApiExplorer.ApiExplorer;
import org.spring.codingStory.bus.service.BusService;
import org.spring.codingStory.buschatbot.apiConfig.ApiConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BusChatBotRestController {


  private final BusService busService;


  @GetMapping("/busChatStation")
  public Map<String,String> busGet(@RequestParam(required = false)String busRouteId) throws IOException {
    String  rs= ApiExplorer.getResponse(busRouteId);
    busService.busStationPostdo(rs);
    Map<String,String> map=new HashMap<>();
    map.put("rs",rs);
    return map;
  }

  @GetMapping("/busArrInfo")
  public Map<String,String> busArrInfo(@RequestParam(required = false)String busRouteId) throws IOException{
    String  rs= ApiConfig.busArrInfo(busRouteId);

    busService.busArrInfo(rs);

    Map<String,String> map=new HashMap<>();
    map.put("rs",rs);
    return map;
  }



}
