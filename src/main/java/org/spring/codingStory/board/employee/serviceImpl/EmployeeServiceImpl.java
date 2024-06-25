package org.spring.codingStory.board.employee.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.employee.dto.EmployeeDto;
import org.spring.codingStory.board.employee.dto.EmployeeFileDto;
import org.spring.codingStory.board.employee.entity.EmployeeEntity;
import org.spring.codingStory.board.employee.entity.EmployeeFileEntity;
import org.spring.codingStory.board.employee.repository.EmpFileRepository;
import org.spring.codingStory.board.employee.repository.EmployeeRepository;
import org.spring.codingStory.board.employee.serviceImpl.service.EmployeeService;
import org.spring.codingStory.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmpFileRepository empFileRepository;

    @Override
    public void empInsertFile(EmployeeDto empDto) throws IOException {
        if (empDto.getBoardFile().isEmpty()) {
            //파일 없는 경우
            empDto.setMemberEntity(MemberEntity.builder()
                    .id(empDto.getMemberId())
                    .build());
            EmployeeEntity empEntity = EmployeeEntity.toInsertEmpEntity(empDto);
            employeeRepository.save(empEntity);
        } else {
            MultipartFile boardFile = empDto.getBoardFile();// 1. 파일을 가져온다. Dto
            String oldFileName = boardFile.getOriginalFilename();// 원본파일 이름
            UUID uuid = UUID.randomUUID(); //random id -> 랜덤한 값을 추출하는 플래스
            String newFileName = uuid + "_" + oldFileName; // 저장파일이름 (보완)
            String filePath = "C:/codingStory_file/" + newFileName; // 실제 파일 저장경로+이름
            //실제파일 저장 실행
            boardFile.transferTo(new File(filePath));//저장, 예외처리 -> 경로에 파일 저장
            empDto.setMemberEntity(MemberEntity.builder()
                    .id(empDto.getMemberId())
                    .build());
            EmployeeEntity empEntity = EmployeeEntity.toInsertFileEmpEntity(empDto);
            Long id = employeeRepository.save(empEntity).getId();
            Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(id);
            if (optionalEmployeeEntity.isPresent()) { // -> 게시글이 존재한다면
                //게시글 0
                EmployeeEntity empEntity1 = optionalEmployeeEntity.get(); //Entity
                EmployeeFileDto fileDto = EmployeeFileDto.builder()
                        .empOldFileName(oldFileName)
                        .empNewFileName(newFileName)
                        .employeeEntity(empEntity1)
                        .build();
                EmployeeFileEntity fileEntity = EmployeeFileEntity.toInsertEmpFile(fileDto);
                empFileRepository.save(fileEntity);

            } else {
                throw new IllegalArgumentException("아이디가 없습니다");
            }
        }
    }



    @Override
    public Page<EmployeeDto> empList(Pageable pageable, String subject1, String subject2, String search) {
        Page<EmployeeEntity> employeeEntityPage = null;

        if (subject1 != null && subject2 != null && search != null) {
            if ("empTitle".equals(subject2)) {
                if ("본사".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpTitleContains(Collections.singletonList("본사"), search, pageable);
                } else if ("노원점".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpTitleContains(Collections.singletonList("노원점"), search, pageable);
                } else if ("자동차관".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpTitleContains(Collections.singletonList("자동차관"), search, pageable);
                } else if ("야외관".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpTitleContains(Collections.singletonList("야외관"), search, pageable);
                } else if ("커플관".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpTitleContains(Collections.singletonList("커플관"), search, pageable);
                }
            } else if ("empContent".equals(subject2)) {
                if ("본사".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpContentContains(Collections.singletonList("본사"), search, pageable);
                } else if ("노원점".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpContentContains(Collections.singletonList("노원점"), search, pageable);
                } else if ("자동차관".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpContentContains(Collections.singletonList("자동차관"), search, pageable);
                } else if ("야외관".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpContentContains(Collections.singletonList("야외관"), search, pageable);
                } else if ("커플관".equals(subject1)) {
                    employeeEntityPage = employeeRepository.findByCategoryInAndEmpContentContains(Collections.singletonList("커플관"), search, pageable);
                }
            }
        } else {
            // null일 경우 기본적으로 findAll 메서드 호출
            return employeeRepository.findAll(pageable).map(EmployeeDto::toEmpDto);
        }
        Page<EmployeeDto> employeeDtoPage = employeeEntityPage.map(EmployeeDto::toEmpDto);
        // noticeEntityPage가 null이 아닌 경우에만 map() 메서드 호출
        return employeeDtoPage;
    }
    @Override
    public EmployeeDto detail(Long Id) {
        Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(Id);
        if (optionalEmployeeEntity.isPresent()) {
            //조회할 게시물이 있으면
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            EmployeeDto employeeDto = EmployeeDto.toEmpDto(employeeEntity);
            return employeeDto;
        }
        throw new IllegalArgumentException("아이다가 fail");
    }

    @Override
    public void empUpdateOk(EmployeeDto employeeDto) {
        //게시물 유무 체크
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정게시물없음"));
        //파일체크
        Optional<EmployeeFileEntity> optionalFileEntity = empFileRepository.findByEmployeeEntityId(employeeDto.getId());
        //파일이 있으면 파일 기존 파일 삭제
        if (optionalFileEntity.isPresent()) {
            String fileNewName = optionalFileEntity.get().getEmpNewFileName();
            String filePath = "C:/codingStory_file/" + fileNewName;
            File deleteFile = new File(filePath);
            if (deleteFile.exists()) {
                deleteFile.delete();
                System.out.println("파일을 삭제하였습니다");
            } else {
                System.out.println("파일이 존재하지않습니다");
            }
            empFileRepository.delete(optionalFileEntity.get());//파일 테이블 레코드 삭제
        }
        //수정
        Optional<EmployeeEntity> optionalShopEntity = employeeRepository.findById(employeeDto.getId());
        MemberEntity memberEntity = MemberEntity.builder().id(employeeDto.getMemberId()).build();
        employeeDto.setMemberEntity(memberEntity);
        if (employeeDto.getBoardFile().isEmpty()) {
            //파일 없는경우
            employeeEntity = EmployeeEntity.toUpdateEmpEntity(employeeDto);
            employeeRepository.save(employeeEntity);
        } else {
            //파일있는경우
            MultipartFile boardFile = employeeDto.getBoardFile();
            String fileOldName = boardFile.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String fileNewName = uuid + "_" + fileOldName;
            String savaPath = "C:/codingStory_file/" + fileNewName;
            try {
                boardFile.transferTo(new File(savaPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            employeeEntity = EmployeeEntity.toUpdateFileEmpEntity(employeeDto);
            employeeRepository.save(employeeEntity);

            EmployeeFileEntity bFileEntity = EmployeeFileEntity.builder()
                    .employeeEntity(employeeEntity)
                    .empNewFileName(fileNewName)
                    .empOldFileName(fileOldName)
                    .build();
            Long fileId = empFileRepository.save(bFileEntity).getId();
            empFileRepository.findById(fileId).orElseThrow(() -> {
                throw new IllegalArgumentException("파일등록 실패");
            });
        }
        //게시글 수정 확인
        employeeRepository.findById(employeeDto.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("게시글 수정실패");
        });
    }


    @Override
    public void empDelete(Long id) {
        EmployeeEntity employeeEntity= employeeRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("삭제할 게시물 없음");});
        employeeRepository.delete(employeeEntity);
    }

    @Override
    public void updateEmpHit(Long id) {
        employeeRepository.updateEmpHit(id);
    }

    @Override
    public List<EmployeeDto> empHit() {
        List<EmployeeEntity> hit = employeeRepository.findTop5ByOrderByEmpHitDesc();

        List<EmployeeDto> employeeDtoList = hit.stream().map(
                EmployeeDto::toEmpDto).collect(Collectors.toList());


        return employeeDtoList;
    }
}

