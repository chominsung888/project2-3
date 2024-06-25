package org.spring.codingStory.board.employee.dto;

import lombok.*;
import org.spring.codingStory.board.employee.entity.EmployeeEntity;
import org.spring.codingStory.board.employee.entity.EmployeeReplyEntity;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeReplyDto {
    private Long id;

    private String empReplyWriter;

    private String empReplyContent;

    private EmployeeEntity employeeEntity;

    private Long employeeId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static EmployeeReplyDto toSelectReplyDto(EmployeeReplyEntity employeeReplyEntity) {
        EmployeeReplyDto employeeReplyDto=new EmployeeReplyDto();
        employeeReplyDto.setId(employeeReplyEntity.getId());
        employeeReplyDto.setEmpReplyContent(employeeReplyEntity.getEmpReplyContent());
        employeeReplyDto.setEmployeeEntity(employeeReplyEntity.getEmployeeEntity());
        employeeReplyDto.setEmpReplyWriter(employeeReplyEntity.getEmpReplyWriter());
        employeeReplyDto.setCreateTime(employeeReplyEntity.getCreateTime());
        employeeReplyDto.setUpdateTime(employeeReplyEntity.getUpdateTime());

        return employeeReplyDto;
    }
}
