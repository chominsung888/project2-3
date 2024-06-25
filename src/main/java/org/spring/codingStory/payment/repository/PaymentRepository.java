package org.spring.codingStory.payment.repository;

import org.spring.codingStory.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    PaymentEntity findByMemberEntity_Id(Long memberId);
}
