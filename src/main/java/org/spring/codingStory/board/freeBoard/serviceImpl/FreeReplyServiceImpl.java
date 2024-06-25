package org.spring.codingStory.board.freeBoard.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.freeBoard.dto.FreeReplyDto;
import org.spring.codingStory.board.freeBoard.entity.FreeEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeReplyEntity;
import org.spring.codingStory.board.freeBoard.repository.FreeReplyRepository;
import org.spring.codingStory.board.freeBoard.repository.FreeRepository;
import org.spring.codingStory.board.freeBoard.serviceImpl.service.FreeReplyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeReplyServiceImpl implements FreeReplyService {

    private final FreeRepository freeRepository;
    private final FreeReplyRepository freeReplyRepository;


    @Override
    public void insertFreeReply(FreeReplyDto freeReplyDto) {
        FreeEntity freeEntity = freeRepository.findById(freeReplyDto.getFreeId()).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 없습니다.");
        });
        //해당 글이 null이 아니라면
        if (freeEntity != null) {
            FreeReplyEntity freeReplyEntity = FreeReplyEntity.builder()
                    .freeEntity(FreeEntity.builder()
                            .id(freeReplyDto.getFreeId()).build()) // 글의 아이디
                    .freeReplyWriter(freeReplyDto.getFreeReplyWriter())
                    .freeReplyContent(freeReplyDto.getFreeReplyContent())
                    .build(); // 찾아서 값 넣고
            freeReplyRepository.save(freeReplyEntity);
        }
    }

    @Override
    public List<FreeReplyDto> freeReplyList(Long id) {
        FreeEntity shopEntity = freeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당글을 찾을 수 없습니다.:" + id));
        List<FreeReplyEntity> shopReplyEntityList = freeReplyRepository.findAllByFreeEntity(shopEntity);

        List<FreeReplyDto> shopReplyDtoList = shopReplyEntityList.stream()
                .map(FreeReplyDto::toSelectReplyDto)
                .collect(Collectors.toList());
        return shopReplyDtoList;
    }

    @Override
    public Long freeReplyDeleteById(Long id) {
        Long freeId = freeReplyRepository.findById(id).get().getFreeEntity().getId(); //댓글 id를 찾아라
        if (freeId != null) {
            freeReplyRepository.deleteById(id);
        } else {
            System.out.println("댓글삭제 실패");
        }
        return freeId;
    }

}
