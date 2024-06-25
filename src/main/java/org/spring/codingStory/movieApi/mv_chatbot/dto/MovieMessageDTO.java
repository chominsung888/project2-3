package org.spring.codingStory.movieApi.mv_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieMessageDTO {

	private String today;
	private String time;
	private MovieAnswerDTO answer;

	public MovieMessageDTO today(String today) {
		this.today = today;
		return this;
	}

	public MovieMessageDTO answer(MovieAnswerDTO answer) {
		this.answer = answer;
		return this;
	}

	
}
