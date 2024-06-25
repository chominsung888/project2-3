package org.spring.codingStory.bus.contoller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.bus.ApiExplorer.ApiExplorer;
import org.spring.codingStory.bus.repository.BusDetailRepository;
import org.spring.codingStory.bus.repository.BusRepository;
import org.spring.codingStory.bus.service.BusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BusController {

  private final BusService busService;
  private final BusRepository busRepository;
  private final BusDetailRepository busDetailRepository;

  @GetMapping("/busList")
  public Map<String,String> busList(@RequestParam(required = false)String strSrch)
                            throws IOException{
        //공공데이터포털 제공 API
    String rs= ApiExplorer.getBusList(strSrch);
    busService.insertResposeBody(rs);
    Map<String,String> map=new HashMap<>();

    map.put("rs",rs);
    return map;
  }

  @GetMapping("/busStationPost")
  public Map<String,String> busGet(@RequestParam(required = false)String busRouteId) throws IOException{
    String  rs=ApiExplorer.getResponse(busRouteId);
    busService.busStationPostdo(rs);
    Map<String,String> map=new HashMap<>();
    map.put("rs",rs);
    return map;
  }

 /* @GetMapping("/busArrivalInfo")
  public Map<String,String> getBusArrivalInfo(@RequestParam String busRouteId) throws IOException {

      String rs = ApiExplorer.fetchBusArrivalInfo(busRouteId);
    busService.busArrInfo(rs);

    Map<String,String> map=new HashMap<>();
    map.put("rs",rs);
    return map;
}
  }*/








}













