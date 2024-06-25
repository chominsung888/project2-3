package org.spring.codingStory.weatherChatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "weather_intention")
@Entity
public class WeatherChatBotIntention {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String name;
	
	@JoinColumn
	@ManyToOne
	private WeatherAnswer weatherAnswer;
		
	@JoinColumn
	@ManyToOne
	private WeatherChatBotIntention upper;
}
