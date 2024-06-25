package org.spring.codingStory.chatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "intention")
@Entity
public class ChatBotIntention {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	
	@JoinColumn
	@ManyToOne
	private AnswerEntity answer;
		
	@JoinColumn
	@ManyToOne
	private ChatBotIntention upper;
}
