package org.spring.codingStory.weatherChatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.weatherChatbot.dto.WeatherAnswerDTO;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather_answer")
@Entity
public class WeatherAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;

	private String content;
	
	private String keyword;    //name
	
	public WeatherAnswer keyword(String keyword) {
		this.keyword=keyword;
		return this;
	}
	
	public WeatherAnswerDTO toAnswerDTO() {
		return WeatherAnswerDTO.builder()
				.no(no).content(content).keyword(keyword)
				.build();
	}

}
