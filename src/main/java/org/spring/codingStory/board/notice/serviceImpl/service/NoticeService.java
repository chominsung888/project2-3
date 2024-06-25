package org.spring.codingStory.board.notice.serviceImpl.service;


import org.spring.codingStory.board.notice.dto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface NoticeService {

    void noticeInsertFile(NoticeDto noticeDto) throws IOException;

    void updateNoticeHit(Long id);

    Page<NoticeDto> noticeList(Pageable pageable, String subject1, String subject2, String search);

    NoticeDto detail(Long Id);

    void noticeUpdateOk(NoticeDto noticeDto);

    void noticeDelete(Long id);

    List<NoticeDto> noticeHit();
}
