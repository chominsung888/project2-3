package org.spring.codingStory.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.pay.entity.PayEntity;

import javax.persistence.*;
import java.util.List;

//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", unique = true)
//    private MemberEntity memberEntity;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    //페이먼트엔티티 연관관계
    //페이먼트엔티티 1:n 페이엔티티
    @JsonIgnore
    @OneToMany(mappedBy = "paymentEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<PayEntity> payEntityList;


    // 기타 필드
    private String hourlyWage;

}
