package org.spring.codingStory.board.freeBoard.serviceImpl.service;

import org.spring.codingStory.board.freeBoard.dto.FreeReplyDto;

import java.util.List;

public interface FreeReplyService {
    void insertFreeReply(FreeReplyDto freeReplyDto);


    List<FreeReplyDto> freeReplyList(Long id);

    Long freeReplyDeleteById(Long id);
}
