package org.spring.codingStory.board.notice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.notice.dto.NoticeReplyDto;
import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.spring.codingStory.board.notice.entity.NoticeReplyEntity;
import org.spring.codingStory.board.notice.repository.NoticeReplyRepository;
import org.spring.codingStory.board.notice.repository.NoticeRepository;
import org.spring.codingStory.board.notice.serviceImpl.service.NoticeReplyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeReplyServiceImpl implements NoticeReplyService {

    private  final NoticeRepository noticeRepository;
    private final NoticeReplyRepository noticeReplyRepository;


    @Override
    public void insertNoticeReply(NoticeReplyDto noticeReplyDto) {
        NoticeEntity noticeEntity= noticeRepository.findById(noticeReplyDto.getNoticeId()).orElseThrow(()->{
            throw new IllegalArgumentException("아이디가 없습니다.");
        });
        //해당 글이 null이 아니라면
        if (noticeEntity != null) {
            NoticeReplyEntity noticeReplyEntity = NoticeReplyEntity.builder()
                    .noticeEntity(NoticeEntity.builder()
                            .id(noticeReplyDto.getNoticeId()).build()) // 글의 아이디
                    .noticeReplyWriter(noticeReplyDto.getNoticeReplyWriter())
                    .noticeReplyContent(noticeReplyDto.getNoticeReplyContent())
                    .build(); // 찾아서 값 넣고
            noticeReplyRepository.save(noticeReplyEntity);
        }
    }

    @Override
    public List<NoticeReplyDto> noticeReplyList(Long id) {
        NoticeEntity shopEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당글을 찾을 수 없습니다.:" + id));
        List<NoticeReplyEntity> shopReplyEntityList = noticeReplyRepository.findAllByNoticeEntity(shopEntity);

        List<NoticeReplyDto> shopReplyDtoList = shopReplyEntityList.stream()
                .map(NoticeReplyDto::toSelectReplyDto)
                .collect(Collectors.toList());
        return shopReplyDtoList;
    }

    @Override
    public Long noticeReplyDeleteById(Long id) {
        Long noticeId = noticeReplyRepository.findById(id).get().getNoticeEntity().getId(); //댓글 id를 찾아라
        if (noticeId != null){
            noticeReplyRepository.deleteById(id);
        }else{
            System.out.println("댓글삭제 실패");
        }
        return noticeId;
    }

}
