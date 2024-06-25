package org.spring.codingStory.board.notice.repository;

import org.spring.codingStory.board.notice.entity.NoticeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeFileRepository extends JpaRepository<NoticeFileEntity,Long> {


    Optional<NoticeFileEntity> findByNoticeEntityId(Long id);
}
