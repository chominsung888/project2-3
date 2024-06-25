package org.spring.codingStory.weatherApi.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Rain {
    
    /*1시간 강우량, mm*/
    @JsonProperty("1h")
    private float rain1h;
    
    /*3시간*/
    @JsonProperty("3h")
    private float rain3h;
}
