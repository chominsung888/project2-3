package org.spring.codingStory.board.notice.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.notice.dto.NoticeDto;
import org.spring.codingStory.board.notice.dto.NoticeFileDto;
import org.spring.codingStory.board.notice.entity.NoticeEntity;
import org.spring.codingStory.board.notice.entity.NoticeFileEntity;
import org.spring.codingStory.board.notice.repository.NoticeFileRepository;
import org.spring.codingStory.board.notice.repository.NoticeRepository;
import org.spring.codingStory.board.notice.serviceImpl.service.NoticeService;
import org.spring.codingStory.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;


    @Override
    public void noticeInsertFile(NoticeDto noticeDto) throws IOException {
        if (noticeDto.getBoardFile().isEmpty()) {
            //파일 없는 경우
            noticeDto.setMemberEntity(MemberEntity.builder()
                    .id(noticeDto.getMemberId())
                    .build());
            NoticeEntity noticeEntity = NoticeEntity.toInsertNoticeEntity(noticeDto);
            noticeRepository.save(noticeEntity);
        } else {
            ////////////////////////////////////파일이 실제 경로에 저장//////////////////////////////
            MultipartFile boardFile = noticeDto.getBoardFile();// 1. 파일을 가져온다. Dto
            // 이름 암호화 -> DB 저장, local에 저장 할 이름
            String oldFileName = boardFile.getOriginalFilename();// 원본파일 이름
            UUID uuid = UUID.randomUUID(); //random id -> 랜덤한 값을 추출하는 플래스
            String newFileName = uuid + "_" + oldFileName; // 저장파일이름 (보완)

            String filePath = "C:/codingStory_file/" + newFileName; // 실제 파일 저장경로+이름
            //실제파일 저장 실행
            boardFile.transferTo(new File(filePath));//저장, 예외처리 -> 경로에 파일 저장

            // 1. 게시글 ->
            noticeDto.setMemberEntity(MemberEntity.builder()
                    .id(noticeDto.getMemberId())
                    .build());
            NoticeEntity noticeEntity = NoticeEntity.toInsertFileNoticeEntity(noticeDto);
            Long id = noticeRepository.save(noticeEntity).getId();

            Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(id);
            if (optionalNoticeEntity.isPresent()) { // -> 게시글이 존재한다면
                //게시글 0
                NoticeEntity noticeEntity1 = optionalNoticeEntity.get(); //Entity

                //게시글이 저장되면 -> 파일을 Entity에 저장

                NoticeFileDto fileDto = NoticeFileDto.builder()
                        .noticeOldFileName(oldFileName)
                        .noticeNewFileName(newFileName)
                        .noticeEntity(noticeEntity1)
                        .build();

                NoticeFileEntity fileEntity = NoticeFileEntity.toInsertNoticeFile(fileDto);

                noticeFileRepository.save(fileEntity);

            } else {
                throw new IllegalArgumentException("아이디가 없습니다");
            }
        }
    }

    @Override
    public void updateNoticeHit(Long id) {
        noticeRepository.updateNoticeHit(id);
    }

    @Override
    public Page<NoticeDto> noticeList(Pageable pageable, String subject1, String subject2, String search) {
        NoticeEntity noticeEntity=new NoticeEntity();
        Page<NoticeEntity> noticeEntityPage = null;

        if (subject1 != null && subject2 != null && search != null) {
            if ("noticeTitle".equals(subject2)) {
                if ("본사".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeTitleContains(Collections.singletonList("본사"), search, pageable);
                } else if ("노원점".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeTitleContains(Collections.singletonList("노원점"), search, pageable);
                } else if ("자동차관".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeTitleContains(Collections.singletonList("자동차관"), search, pageable);
                } else if ("야외관".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeTitleContains(Collections.singletonList("야외관"), search, pageable);
                } else if ("커플관".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeTitleContains(Collections.singletonList("커플관"), search, pageable);
                }
            } else if ("noticeContent".equals(subject2)) {
                if ("본사".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeContentContains(Collections.singletonList("본사"), search, pageable);
                } else if ("노원점".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeContentContains(Collections.singletonList("노원점"), search, pageable);
                } else if ("자동차관".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeContentContains(Collections.singletonList("자동차관"), search, pageable);
                } else if ("야외관".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeContentContains(Collections.singletonList("야외관"), search, pageable);
                } else if ("커플관".equals(subject1)) {
                    noticeEntityPage = noticeRepository.findByCategoryInAndNoticeContentContains(Collections.singletonList("커플관"), search, pageable);
                }
            }
        } else {
            // null일 경우 기본적으로 findAll 메서드 호출
            return noticeRepository.findAll(pageable).map(NoticeDto::toNoticeDto);
        }
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);
        // noticeEntityPage가 null이 아닌 경우에만 map() 메서드 호출
        return noticeDtoPage;
    }

    @Override
    public NoticeDto detail(Long Id) {
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(Id);
        if (optionalNoticeEntity.isPresent()) {
            //조회할 게시물이 있으면
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDto noticeDto = NoticeDto.toNoticeDto(noticeEntity);
            return noticeDto;
        }
        throw new IllegalArgumentException("아이다가 fail");
    }

    @Override
    public void noticeUpdateOk(NoticeDto noticeDto) {
        //게시물 유무 체크
        NoticeEntity noticeEntity = noticeRepository.findById(noticeDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정게시물없음"));
        //파일체크
        Optional<NoticeFileEntity> optionalFileEntity = noticeFileRepository.findByNoticeEntityId(noticeDto.getId());
        //파일이 있으면 파일 기존 파일 삭제
        if (optionalFileEntity.isPresent()) {
            String fileNewName = optionalFileEntity.get().getNoticeNewFileName();
            String filePath = "C:/codingStory_file/" + fileNewName;
            File deleteFile = new File(filePath);
            if (deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제하였습니다");
            } else {
                System.out.println("파일이 존재하지않습니다");
            }
            noticeFileRepository.delete(optionalFileEntity.get());//파일 테이블 레코드 삭제
        }
        //수정
        Optional<NoticeEntity> optionalFreeEntity = noticeRepository.findById(noticeDto.getId());
        MemberEntity memberEntity = MemberEntity.builder().id(noticeDto.getMemberId()).build();
        noticeDto.setMemberEntity(memberEntity);
        if (noticeDto.getBoardFile().isEmpty()) {
            //파일 없는경우
            noticeEntity = NoticeEntity.toUpdateNoticeEntity(noticeDto);
            noticeRepository.save(noticeEntity);
        } else {
            //파일있는경우
            MultipartFile boardFile = noticeDto.getBoardFile();
            String fileOldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileNewName = uuid + "_" + fileOldName;
            String savaPath = "C:/codingStory_file/" + fileNewName;
            try {
                boardFile.transferTo(new File(savaPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            noticeEntity = NoticeEntity.toUpdateFileNoticeEntity(noticeDto);
            noticeRepository.save(noticeEntity);

            NoticeFileEntity bFileEntity = NoticeFileEntity.builder()
                    .noticeEntity(noticeEntity)
                    .noticeNewFileName(fileNewName)
                    .noticeOldFileName(fileOldName)
                    .build();
            Long fileId = noticeFileRepository.save(bFileEntity).getId();
            noticeFileRepository.findById(fileId).orElseThrow(() -> {
                throw new IllegalArgumentException("파일등록 실패");
            });
        }
        //게시글 수정 확인
        noticeRepository.findById(noticeDto.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글 수정실패");
        });
    }

    @Override
    public void noticeDelete(Long id) {
        NoticeEntity noticeEntity= noticeRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("삭제할 게시물 없음");});
        noticeRepository.delete(noticeEntity);
    }


    @Override
    public List<NoticeDto> noticeHit() {
        List<NoticeEntity> hit = noticeRepository.findTop5ByOrderByNoticeHitDesc();

        List<NoticeDto> noticeDtoList = hit.stream().map(
                NoticeDto::toNoticeDto).collect(Collectors.toList());


        return noticeDtoList;
    }
}
