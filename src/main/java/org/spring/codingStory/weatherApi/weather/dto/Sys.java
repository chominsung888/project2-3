package org.spring.codingStory.weatherApi.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sys {

    private String type;
    private String id;
    private String country;
    private String sunrise;
    private String sunset;
}