package org.spring.codingStory.payment.dto;

import lombok.*;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.payment.entity.PaymentEntity;

//@Transactional
//@Service
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaymentDto {

    private Long id;

    private MemberEntity memberEntity;

    private String hourlyWage;


    public static PaymentDto toSelectAllPaymentList(PaymentEntity paymentEntity) {

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(paymentEntity.getId());
        paymentDto.setMemberEntity(paymentEntity.getMemberEntity());
        paymentDto.setHourlyWage(paymentEntity.getHourlyWage());

        return paymentDto;

    }
}
