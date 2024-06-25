package org.spring.codingStory.movieApi.mv_chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.movieApi.mv_chatbot.dto.MovieAnswerDTO;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movieAnswer")
@Entity
public class MovieAnswerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;
	
	private String keyword;    //name
	
	public MovieAnswerEntity keyword(String keyword) {
		this.keyword=keyword;
		return this;
	}
	
	public MovieAnswerDTO toAnswerDTO() {
		return MovieAnswerDTO.builder()
				.id(id).content(content).keyword(keyword)
				.build();
	}

}
