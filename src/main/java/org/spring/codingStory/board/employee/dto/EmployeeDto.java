package org.spring.codingStory.board.employee.dto;

import lombok.*;
import org.spring.codingStory.board.employee.entity.EmployeeEntity;
import org.spring.codingStory.board.employee.entity.EmployeeFileEntity;
import org.spring.codingStory.board.employee.entity.EmployeeReplyEntity;
import org.spring.codingStory.member.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeDto {

    private Long id;

    private String category;

    private String empTitle;

    private String empContent;

    private int empHit;

    private String empWriter;

    private int empAttachFile;

    private MultipartFile boardFile;

    private MemberEntity memberEntity;

    private List<EmployeeFileEntity> employeeFileEntityList;

    private List<EmployeeReplyEntity> employeeReplyEntityList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long memberId;

    private String empNewFileName;

    private String empOldFileName;

    public static EmployeeDto toEmpDto(EmployeeEntity empEntity) {
        EmployeeDto boardDto = new EmployeeDto();

        boardDto.setId(empEntity.getId());
        boardDto.setEmpContent(empEntity.getEmpContent());
        boardDto.setEmpTitle(empEntity.getEmpTitle());
        boardDto.setCategory(empEntity.getCategory());
        boardDto.setEmpWriter(empEntity.getMemberEntity().getName()); // 작성말고 회원의 이름정보만 받아오기?
        boardDto.setEmpHit(empEntity.getEmpHit());
        boardDto.setEmpAttachFile(empEntity.getEmpAttachFile());
        boardDto.setCreateTime(empEntity.getCreateTime());
        boardDto.setUpdateTime(empEntity.getUpdateTime());
        boardDto.setMemberEntity(empEntity.getMemberEntity());
        if(empEntity.getEmpAttachFile()==0) {
            //파일0
            boardDto.setEmpAttachFile(empEntity.getEmpAttachFile());
        }else{
            boardDto.setEmpAttachFile(empEntity.getEmpAttachFile());
            //새파일
            boardDto.setEmpNewFileName(empEntity.getEmployeeFileEntityList().get(0).getEmpNewFileName());
            //원본파일
            boardDto.setEmpOldFileName(empEntity.getEmployeeFileEntityList().get(0).getEmpOldFileName());
        }
        return boardDto;

    }
}
