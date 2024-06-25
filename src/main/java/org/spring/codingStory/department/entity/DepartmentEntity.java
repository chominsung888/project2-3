package org.spring.codingStory.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.member.entity.MemberEntity;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @Column(nullable = false,unique = true)
    private String dptName;

    @Column(nullable = false)
    private String location; // 부서 위치 필드 추가


    @Column(nullable = false)
    private int memberCount;

    //멤버 수를 업데이트하는 메서드 추가


    public void updateMemberCount(){
        this.memberCount=countMembers();
    }


    // 소속 인원 수를 반환하는 메서드
    public int countMembers() {
        return memberEntityList != null ? memberEntityList.size() : 0;
    }


    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "departmentEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<MemberEntity> memberEntityList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_department_id") //부모 부서의 iD를 가리키는 외래키
    private DepartmentEntity parentDepartment;

    @OneToMany(mappedBy = "parentDepartment", fetch = FetchType.LAZY)
    private List<DepartmentEntity> childDepartments;


}
