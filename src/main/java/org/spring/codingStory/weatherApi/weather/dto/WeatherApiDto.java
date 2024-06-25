package org.spring.codingStory.weatherApi.weather.dto;

import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WeatherApiDto {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private String visibility;
    private Wind wind;
    private Clouds clouds;
    private String dt;
    private Sys sys;
    private String timezone;
    private String id;
    private String name;
    private String cod;
    private Rain rain;
}

/*  날씨 api에서 받아온 필드 전부 dto로 만듬. 객체가 있으면 클래스로 만들어 줘야 함, 배열[]은 list로.

 //광주, 춘천.. -> main에 sea_level, grnd_level, Wind에 gust..
  도시에 따라서 필요한 거 넣어야 불러와짐
  필요한거 다 불러와야 하지만, entity에 set 할 필요는 x
    private String gust;


  {
  "coord": {	//안에 필드 있으면 클래스 만듬
    "lon": 126.9778,
    "lat": 37.5683
  },
  "weather": [	//배열은 리스트로 만듬
    {
      "id": 802,
      "main": "Clouds",
      "description": "scattered clouds",
      "icon": "03d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 298.11,
    "feels_like": 298.36,
    "temp_min": 295.84,
    "temp_max": 298.81,
    "pressure": 1009,
    "humidity": 65
  },
  "visibility": 6000,
  "wind": {
    "speed": 3.09,
    "deg": 180
  },
  "clouds": {
    "all": 40
  },
  "dt": 1718066196,
  "sys": {
    "type": 1,
    "id": 8105,
    "country": "KR",
    "sunrise": 1718050225,
    "sunset": 1718103195
  },
  "timezone": 32400,
  "id": 1835848,
  "name": "Seoul",
  "cod": 200
}

 */
