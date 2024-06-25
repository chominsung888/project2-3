package org.spring.codingStory.weatherChatbot.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TempInfo {

//	private String deptName;
	private String weatherName;
	private String temp;


	private String tempMax;
	private String humidity;

}
