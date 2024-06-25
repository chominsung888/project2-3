package org.spring.codingStory.chatbot.repository;

import org.spring.codingStory.chatbot.entity.ChatBotIntention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatBotIntentionRepository extends JpaRepository<ChatBotIntention, Long>{

//	Optional<ChatBotIntention> findByName(String token);

	Optional<ChatBotIntention> findByNameAndUpper(String token, ChatBotIntention upper);

}
