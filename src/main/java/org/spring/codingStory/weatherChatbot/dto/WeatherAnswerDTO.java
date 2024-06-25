package org.spring.codingStory.weatherChatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeatherAnswerDTO {
	
	private long no;

	private String content;

	private String keyword;//name

	///////////////////////////////////////////////////////////

	private TempInfo temp;

	private List<TempInfo> tempInfoList;

	public WeatherAnswerDTO temp(TempInfo temp){
		this.temp=temp;
		return this;
	}

}
