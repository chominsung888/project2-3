package org.spring.codingStory.board.freeBoard.repository;

import org.spring.codingStory.board.freeBoard.entity.FreeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeFileRepository extends JpaRepository<FreeFileEntity,Long> {


    Optional<FreeFileEntity> findByFreeEntityId(Long id);
}
