package org.spring.codingStory.approval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "apvDiv")
public class ApprovalDivEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apvDiv_id")
    private Long id;

    @Column(nullable = false)
    private String apvDivName;
    //보고서의 종류

    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "approvalDivEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<ApprovalEntity> approvalEntityList;

}
