package org.spring.codingStory.board.freeBoard.serviceImpl.service;


import org.spring.codingStory.board.freeBoard.dto.FreeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface FreeService {

    void freeInsertFile(FreeDto freeDto) throws IOException;

    void updateFreeHit(Long id);

    Page<FreeDto> freeList(Pageable pageable, String subject1, String subject2, String search);

    FreeDto detail(Long Id);

    void freeUpdateOk(FreeDto freeDto);

    void freeDelete(Long id);

    List<FreeDto> freeHit();
}
