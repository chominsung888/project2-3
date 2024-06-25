package org.spring.codingStory.movieApi.mv_chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movieintention")
@Entity
public class MovieChatBotIntention {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@JoinColumn
	@ManyToOne
	private MovieAnswerEntity answer;
		
	@JoinColumn
	@ManyToOne
	private MovieChatBotIntention upper;
}
