package org.spring.codingStory.board.freeBoard.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.freeBoard.dto.FreeDto;
import org.spring.codingStory.board.freeBoard.dto.FreeFileDto;
import org.spring.codingStory.board.freeBoard.entity.FreeEntity;
import org.spring.codingStory.board.freeBoard.entity.FreeFileEntity;
import org.spring.codingStory.board.freeBoard.repository.FreeFileRepository;
import org.spring.codingStory.board.freeBoard.repository.FreeRepository;
import org.spring.codingStory.board.freeBoard.serviceImpl.service.FreeService;
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
public class FreeServiceImpl implements FreeService {

    private final FreeRepository freeRepository;
    private final FreeFileRepository freeFileRepository;


    @Override
    public void freeInsertFile(FreeDto freeDto) throws IOException {
        if (freeDto.getBoardFile().isEmpty()) {
            //파일 없는 경우
            freeDto.setMemberEntity(MemberEntity.builder()
                    .id(freeDto.getMemberId())
                    .build());
            FreeEntity freeEntity = FreeEntity.toInsertFreeEntity(freeDto);
            freeRepository.save(freeEntity);
        } else {
            ////////////////////////////////////파일이 실제 경로에 저장//////////////////////////////
            MultipartFile boardFile = freeDto.getBoardFile();// 1. 파일을 가져온다. Dto
            // 이름 암호화 -> DB 저장, local에 저장 할 이름
            String oldFileName = boardFile.getOriginalFilename();// 원본파일 이름
            UUID uuid = UUID.randomUUID(); //random id -> 랜덤한 값을 추출하는 플래스
            String newFileName = uuid + "_" + oldFileName; // 저장파일이름 (보완)

            String filePath = "C:/CodingStory_file/" + newFileName; // 실제 파일 저장경로+이름
            //실제파일 저장 실행
            boardFile.transferTo(new File(filePath));//저장, 예외처리 -> 경로에 파일 저장

            // 1. 게시글 ->
            freeDto.setMemberEntity(MemberEntity.builder()
                    .id(freeDto.getMemberId())
                    .build());
            FreeEntity freeEntity = FreeEntity.toInsertFileFreeEntity(freeDto);
            Long id = freeRepository.save(freeEntity).getId();

            Optional<FreeEntity> optionalFreeEntity = freeRepository.findById(id);
            if (optionalFreeEntity.isPresent()) { // -> 게시글이 존재한다면
                //게시글 0
                FreeEntity freeEntity1 = optionalFreeEntity.get(); //Entity

                //게시글이 저장되면 -> 파일을 Entity에 저장

                FreeFileDto fileDto = FreeFileDto.builder()
                        .freeOldFileName(oldFileName)
                        .freeNewFileName(newFileName)
                        .freeEntity(freeEntity1)
                        .build();

                FreeFileEntity fileEntity = FreeFileEntity.toInsertFreeFile(fileDto);

                freeFileRepository.save(fileEntity);

            } else {
                throw new IllegalArgumentException("아이디가 없습니다");
            }
        }
    }

    @Override
    public void updateFreeHit(Long id) {
        freeRepository.updateFreeHit(id);
    }

    @Override
    public Page<FreeDto> freeList(Pageable pageable, String subject1, String subject2, String search) {
        Page<FreeEntity> freeEntityPage = null;

        if (subject1 != null && subject2 != null && search != null) {
            if ("freeTitle".equals(subject2)) {
                if ("본사".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeTitleContains(Collections.singletonList("본사"), search, pageable);
                } else if ("노원점".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeTitleContains(Collections.singletonList("노원점"), search, pageable);
                } else if ("자동차관".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeTitleContains(Collections.singletonList("자동차관"), search, pageable);
                } else if ("야외관".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeTitleContains(Collections.singletonList("야외관"), search, pageable);
                } else if ("커플관".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeTitleContains(Collections.singletonList("커플관"), search, pageable);
                }
            } else if ("freeContent".equals(subject2)) {
                if ("본사".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeContentContains(Collections.singletonList("본사"), search, pageable);
                } else if ("노원점".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeContentContains(Collections.singletonList("노원점"), search, pageable);
                } else if ("자동차관".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeContentContains(Collections.singletonList("자동차관"), search, pageable);
                } else if ("야외관".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeContentContains(Collections.singletonList("야외관"), search, pageable);
                } else if ("커플관".equals(subject1)) {
                    freeEntityPage = freeRepository.findByCategoryInAndFreeContentContains(Collections.singletonList("커플관"), search, pageable);
                }
            }
        } else {
            freeEntityPage = freeRepository.findAll(pageable);
        }

        return freeEntityPage.map(FreeDto::toFreeDto);
    }

    @Override
    public FreeDto detail(Long Id) {
        Optional<FreeEntity> optionalFreeEntity = freeRepository.findById(Id);
        if (optionalFreeEntity.isPresent()) {
            //조회할 게시물이 있으면
            FreeEntity freeEntity = optionalFreeEntity.get();
            FreeDto freeDto = FreeDto.toFreeDto(freeEntity);
            return freeDto;
        }
        throw new IllegalArgumentException("아이다가 fail");
    }

    @Override
    public void freeUpdateOk(FreeDto freeDto) {
        //게시물 유무 체크
        FreeEntity freeEntity = freeRepository.findById(freeDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정게시물없음"));
        //파일체크
        Optional<FreeFileEntity> optionalFileEntity = freeFileRepository.findByFreeEntityId(freeDto.getId());
        //파일이 있으면 파일 기존 파일 삭제
        if (optionalFileEntity.isPresent()) {
            String fileNewName = optionalFileEntity.get().getFreeNewFileName();
            String filePath = "C:/codingStory_file/" + fileNewName;
            File deleteFile = new File(filePath);
            if (deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제하였습니다");
            } else {
                System.out.println("파일이 존재하지않습니다");
            }
            freeFileRepository.delete(optionalFileEntity.get());//파일 테이블 레코드 삭제
        }
        //수정
        Optional<FreeEntity> optionalFreeEntity = freeRepository.findById(freeDto.getId());
        MemberEntity memberEntity = MemberEntity.builder().id(freeDto.getMemberId()).build();
        freeDto.setMemberEntity(memberEntity);
        if (freeDto.getBoardFile().isEmpty()) {
            //파일 없는경우
            freeEntity = FreeEntity.toUpdateFreeEntity(freeDto);
            freeRepository.save(freeEntity);
        } else {
            //파일있는경우
            MultipartFile boardFile = freeDto.getBoardFile();
            String fileOldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileNewName = uuid + "_" + fileOldName;
            String savaPath = "C:/codingStory_file/" + fileNewName;
            try {
                boardFile.transferTo(new File(savaPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            freeEntity = FreeEntity.toUpdateFileFreeEntity(freeDto);
            freeRepository.save(freeEntity);

            FreeFileEntity bFileEntity = FreeFileEntity.builder()
                    .freeEntity(freeEntity)
                    .freeNewFileName(fileNewName)
                    .freeOldFileName(fileOldName)
                    .build();
            Long fileId = freeFileRepository.save(bFileEntity).getId();
            freeFileRepository.findById(fileId).orElseThrow(() -> {
                throw new IllegalArgumentException("파일등록 실패");
            });
        }
        //게시글 수정 확인
        freeRepository.findById(freeDto.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글 수정실패");
        });
    }

    @Override
    public void freeDelete(Long id) {
        FreeEntity freeEntity= freeRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("삭제할 게시물 없음");});
        freeRepository.delete(freeEntity);
    }

    @Override
    public List<FreeDto> freeHit() {

        List<FreeEntity> hit = freeRepository.findTop5ByOrderByFreeHitDesc();

        List<FreeDto> freeDtoList = hit.stream().map(
                FreeDto::toFreeDto).collect(Collectors.toList());


        return freeDtoList;
    }
}

