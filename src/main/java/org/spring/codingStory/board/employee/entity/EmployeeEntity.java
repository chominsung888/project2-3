package org.spring.codingStory.board.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.board.employee.dto.EmployeeDto;
import org.spring.codingStory.contraint.BaseTimeEntity;
import org.spring.codingStory.member.entity.MemberEntity;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String empTitle;

    @Column(nullable = false)
    private String empContent;

    @Column(nullable = false)
    private String empWriter;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int empHit;

    @Column(nullable = false)
    private int empAttachFile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "employeeEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<EmployeeFileEntity> employeeFileEntityList;

    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "employeeEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<EmployeeReplyEntity> employeeReplyEntityList;


    public static EmployeeEntity toInsertEmpEntity(EmployeeDto empDto) {
        EmployeeEntity empEntity=new EmployeeEntity();
        empEntity.setId(empDto.getId());
        empEntity.setEmpContent(empDto.getEmpContent());
        empEntity.setEmpTitle(empDto.getEmpTitle());
        empEntity.setCategory(empDto.getCategory());
        empEntity.setEmpWriter(empDto.getEmpWriter());
        empEntity.setEmpHit(0);
        empEntity.setEmpAttachFile(0);
        empEntity.setMemberEntity(empDto.getMemberEntity());

        return empEntity;

    }

    public static EmployeeEntity toInsertFileEmpEntity(EmployeeDto empDto) {

        EmployeeEntity empEntity=new EmployeeEntity();
        empEntity.setId(empDto.getId());
        empEntity.setEmpContent(empDto.getEmpContent());
        empEntity.setEmpTitle(empDto.getEmpTitle());
        empEntity.setCategory(empDto.getCategory());
        empEntity.setEmpWriter(empDto.getEmpWriter());
        empEntity.setEmpHit(0);
        empEntity.setEmpAttachFile(1);
        empEntity.setMemberEntity(empDto.getMemberEntity());

        return empEntity;

    }

    public static EmployeeEntity toUpdateEmpEntity(EmployeeDto empDto) {
        EmployeeEntity empEntity=new EmployeeEntity();
        empEntity.setId(empDto.getId());
        empEntity.setEmpContent(empDto.getEmpContent());
        empEntity.setEmpTitle(empDto.getEmpTitle());
        empEntity.setCategory(empDto.getCategory());
        empEntity.setEmpWriter(empDto.getEmpWriter());
        empEntity.setEmpAttachFile(0);
        empEntity.setMemberEntity(empDto.getMemberEntity());
        return empEntity;

    }
    public static EmployeeEntity toUpdateFileEmpEntity(EmployeeDto empDto) {
        EmployeeEntity empEntity=new EmployeeEntity();
        empEntity.setId(empDto.getId());
        empEntity.setEmpContent(empDto.getEmpContent());
        empEntity.setEmpTitle(empDto.getEmpTitle());
        empEntity.setCategory(empDto.getCategory());
        empEntity.setEmpWriter(empDto.getEmpWriter());
        empEntity.setEmpAttachFile(1);
        empEntity.setMemberEntity(empDto.getMemberEntity());

        return empEntity;
    }


}
