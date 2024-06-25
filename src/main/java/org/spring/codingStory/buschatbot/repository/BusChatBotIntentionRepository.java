package org.spring.codingStory.buschatbot.repository;


import org.spring.codingStory.buschatbot.entity.BusIntention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusChatBotIntentionRepository extends JpaRepository<BusIntention, Long> {

	Optional<BusIntention> findByName(String token);

	Optional<BusIntention> findByNameAndUpper(String token, BusIntention upper);


}
