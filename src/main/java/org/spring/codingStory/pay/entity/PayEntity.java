package org.spring.codingStory.pay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.contraint.BaseTimeEntity;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.pay.dto.PayDto;
import org.spring.codingStory.payment.entity.PaymentEntity;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "pay")
public class PayEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    //산정시작일

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    //산정종료일

    //페이엔티티 연관관계
    // 페이엔티티 n:1 페이먼트엔티티
    @JsonIgnore //ajax 시 순환 참조 방지
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentEntity;

    /////////////////////////////////////

    @Column
    private Time calcTime;
    //산정기간(PayEntity) 내 workTime(AttendanceEntity)

    @Column
    private Double payInDur;
    // 기간 내 급여

    @Column
    private Double payBns;

    @Column
    private Double totalPay;

    @Column
    private LocalDate payingDate;

    public static PayEntity toInsertPayEntity(PayDto payDto, Time calcTime, PaymentEntity paymentEntity) {
        PayEntity payEntity = new PayEntity();
        payEntity.setMemberEntity(payDto.getMemberEntity());
        payEntity.setPaymentEntity(paymentEntity); // 추가된 부분
        payEntity.setStartDate(payDto.getStartDate());
        payEntity.setEndDate(payDto.getEndDate());
        payEntity.setCalcTime(calcTime);
        payEntity.setPayInDur(payDto.getPayInDur());
        payEntity.setPayBns(payDto.getPayBns());

        Double totalPay = (double) Math.round( (payDto.getPayBns() != null ? payDto.getPayBns() : 0) + (payDto.getPayInDur() != null ? payDto.getPayInDur() : 0) );
        payEntity.setTotalPay(totalPay);
        payEntity.setPayingDate(payDto.getPayingDate());
        return payEntity;
    }

}
