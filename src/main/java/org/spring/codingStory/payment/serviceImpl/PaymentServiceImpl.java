package org.spring.codingStory.payment.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.payment.dto.PaymentDto;
import org.spring.codingStory.payment.entity.PaymentEntity;
import org.spring.codingStory.payment.repository.PaymentRepository;
import org.spring.codingStory.payment.serviceImpl.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentDto> paymentList() {
        List<PaymentEntity> paymentEntities = paymentRepository.findAll();
        List<PaymentDto> paymentDtoList = new ArrayList<>();

        if (!paymentEntities.isEmpty()) {

            for (PaymentEntity paymentEntity : paymentEntities) {
                // file정보 get
                PaymentDto paymentDto = PaymentDto.toSelectAllPaymentList(paymentEntity);
                System.out.println(paymentDto);

                paymentDtoList.add((paymentDto));
            }
            return paymentDtoList;
        }
//    throw  new IllegalArgumentException("예외 발생 !");
        return null;
    }
}
