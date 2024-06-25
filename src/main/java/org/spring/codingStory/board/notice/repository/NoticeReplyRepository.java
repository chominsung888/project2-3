package org.spring.codingStory.board.notice.repository;

import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.spring.codingStory.board.notice.entity.NoticeReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeReplyRepository extends JpaRepository<NoticeReplyEntity,Long> {

    List<NoticeReplyEntity> findAllByNoticeEntity(NoticeEntity noticeEntity);
}
