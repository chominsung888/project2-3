package org.spring.codingStory.board.freeBoard.repository;

import org.spring.codingStory.board.freeBoard.entity.FreeEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeReplyRepository extends JpaRepository<FreeReplyEntity,Long> {
    List<FreeReplyEntity> findAllByFreeEntity(FreeEntity freeEntity);

}
