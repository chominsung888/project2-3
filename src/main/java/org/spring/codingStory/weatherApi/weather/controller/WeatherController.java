package org.spring.codingStory.weatherApi.weather.controller;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.weatherApi.util.OpenApiUtil;
import org.spring.codingStory.weatherApi.weather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weatherList")
    public ResponseEntity<Map<String, String>> weatherList(@RequestParam double lat, @RequestParam double lon) {

        String appid = "d5254c5da49cec7a1b213e7c60158197";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + appid;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        // openApi Data -> GET
        String responseBody = OpenApiUtil.get(apiUrl, requestHeaders);

        // 1. db 저장
        weatherService.insertResponseBody(responseBody);

        // view의 리턴값
        Map<String, String> weatherList = new HashMap<>();
        weatherList.put("weatherList", responseBody);

        System.out.println("responseBody: " + responseBody);

        return ResponseEntity.status(HttpStatus.OK).body(weatherList);
    }
}
