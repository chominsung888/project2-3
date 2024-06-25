package org.spring.codingStory.board.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.board.employee.dto.EmployeeFileDto;
import org.spring.codingStory.contraint.BaseTimeEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "emp_file")
public class EmployeeFileEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_file_id")
    private Long id;

    @Column(nullable = false)
    private String empNewFileName;

    @Column(nullable = false)
    private String empOldFileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeEntity;

    public static EmployeeFileEntity toInsertEmpFile(EmployeeFileDto empFileDto) {
        EmployeeFileEntity empFileEntity=new EmployeeFileEntity();
        empFileEntity.setEmpNewFileName(empFileDto.getEmpNewFileName());
        empFileEntity.setEmpOldFileName(empFileDto.getEmpOldFileName());
        empFileEntity.setEmployeeEntity(empFileDto.getEmployeeEntity());

        return empFileEntity;
    }
}
