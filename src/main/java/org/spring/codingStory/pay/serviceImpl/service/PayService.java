package org.spring.codingStory.pay.serviceImpl.service;

import org.spring.codingStory.pay.dto.PayDto;

import java.util.List;

public interface PayService {
    void insertPay(PayDto payDto);

    PayDto detail(Long id);
//
//    Page<PayDto> paySearchPagingList(Pageable pageable, String subject, String search);

    List<PayDto> findByMemberId(Long memberId);

    int updateOk(PayDto payDto);
}
