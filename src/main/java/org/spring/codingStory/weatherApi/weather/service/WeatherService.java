package org.spring.codingStory.weatherApi.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.codingStory.weatherApi.weather.dto.Weather;
import org.spring.codingStory.weatherApi.weather.dto.WeatherApiDto;
import org.spring.codingStory.weatherApi.weather.entity.WeatherEntity;
import org.spring.codingStory.weatherApi.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public void insertResponseBody(String responseBody) {

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("<< responseBody" + responseBody);

        WeatherApiDto response = null;

        try {                                           //json 데이터를 클래스로 이동
            response = objectMapper.readValue(responseBody, WeatherApiDto.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<< WeatherApiDto " + response);

        WeatherEntity weatherEntity = WeatherEntity.builder()
                .name(response.getName())
                .lat(response.getCoord().getLat())
                .lon(response.getCoord().getLon())
                .country(response.getSys().getCountry())
                .temp_max(response.getMain().getTemp_max())
                .temp_min(response.getMain().getTemp_min())


                .sea_level(response.getMain().getSea_level())
                .grnd_level(response.getMain().getGrnd_level())


                .humidity(response.getMain().getHumidity())

                .description(response.getWeather().get(0).getDescription())
                .temp(response.getMain().getTemp())
                .speed(response.getWind().getSpeed())
                .clouds_all(response.getClouds().getAll())


                .build();

        //없을 경우 저장
        Optional<WeatherEntity> optionalWeatherEntity
                =weatherRepository.findByName(response.getName());

                if(!optionalWeatherEntity.isPresent()){
                weatherRepository.save(weatherEntity);}

        List<Weather> weatherList=response.getWeather()
                .stream()
                .collect(Collectors.toList());

    }
}
