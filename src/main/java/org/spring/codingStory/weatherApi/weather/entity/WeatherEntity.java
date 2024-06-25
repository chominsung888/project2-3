package org.spring.codingStory.weatherApi.weather.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "weather")
public class WeatherEntity {

    //db저장 시킬 실제 필요한 데이터만 필드값으로 저장
    @Id
    @Column(name="weather_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;    //도시이름
    private String lat;
    private String lon;
    private String country; //국가
    private String temp_max;
    private String temp_min;

    private String sea_level;
    private String grnd_level;

    private String humidity;

    private String temp;
    private String description;
    private String speed;
    private String clouds_all;

}
