package org.spring.codingStory.board.notice.repository;

import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity,Long> {

    @Modifying
    @Query(value = " update NoticeEntity b set b.noticeHit=b.noticeHit+1  where b.id= :id ")
    void updateNoticeHit(@Param("id") Long id);

    Page<NoticeEntity> findByCategoryInAndNoticeTitleContains(List<String> categories, String search, Pageable pageable);

    Page<NoticeEntity> findByCategoryInAndNoticeContentContains(List<String> categories, String search, Pageable pageable);

    List<NoticeEntity> findTop5ByOrderByNoticeHitDesc();
}
