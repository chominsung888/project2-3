package org.spring.codingStory.movieApi.mv_chatbot.repository;

import org.spring.codingStory.movieApi.mv_chatbot.entity.MovieChatBotIntention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieChatBotIntentionRepository extends JpaRepository<MovieChatBotIntention, Long>{


	Optional<MovieChatBotIntention> findByNameAndUpper(String token, MovieChatBotIntention upper);

}
