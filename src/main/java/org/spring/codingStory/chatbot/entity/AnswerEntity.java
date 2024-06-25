package org.spring.codingStory.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.chatbot.dto.AnswerDTO;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answer")
@Entity
public class AnswerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String content;
	
	private String keyword;    //name
	
	public AnswerEntity keyword(String keyword) {
		this.keyword=keyword;
		return this;
	}
	
	public AnswerDTO toAnswerDTO() {
		return AnswerDTO.builder()
				.id(id).content(content).keyword(keyword)
				.build();
	}

}
