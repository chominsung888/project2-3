package org.spring.codingStory.weatherApi.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wind {

    private String speed;
    private String deg;

    //필요한거 다 불러와야 하지만, entity에 set 할 필요는 x
    private String gust;


}
