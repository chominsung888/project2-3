package org.spring.codingStory.weatherChatbot.repository;

import org.spring.codingStory.weatherChatbot.entity.WeatherChatBotIntention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherChatBotIntentionRepository extends JpaRepository<WeatherChatBotIntention, Long>{

	Optional<WeatherChatBotIntention> findByName(String token);

	Optional<WeatherChatBotIntention> findByNameAndUpper(String token, WeatherChatBotIntention upper);

}
