package org.spring.codingStory.weatherChatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeatherMessageDTO {

	private String today;

	private String time;

	private WeatherAnswerDTO answer;

	public WeatherMessageDTO today(String today) {
		this.today=today;
		return this;
	}
	public WeatherMessageDTO answer(WeatherAnswerDTO answer) {
		this.answer=answer;
		return this;
	}
}
