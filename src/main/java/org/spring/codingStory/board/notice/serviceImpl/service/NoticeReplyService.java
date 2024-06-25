package org.spring.codingStory.board.notice.serviceImpl.service;

import org.spring.codingStory.board.notice.dto.NoticeReplyDto;

import java.util.List;

public interface NoticeReplyService {
    void insertNoticeReply(NoticeReplyDto noticeReplyDto);


    List<NoticeReplyDto> noticeReplyList(Long id);

    Long noticeReplyDeleteById(Long id);
}
