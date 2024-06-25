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
@Table(name = "apv_status")
public class ApprovalStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apvStatus_id")
    private Long id;

    @Column(nullable = false)
    private String apvStatus;

    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "approvalStatusEntity"
        , fetch = FetchType.LAZY
        , cascade = CascadeType.REMOVE)
    private List<ApprovalEntity> approvalEntityList;

}
