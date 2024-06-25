package org.spring.codingStory.approval.serviceImpl.service;

import org.spring.codingStory.approval.dto.ApprovalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.io.IOException;

public interface ApprovalService {
    void apvWrite(ApprovalDto approvalDto) throws IOException;

    // 내가 결재자인 보고서
    @Transactional
    Page<ApprovalDto> apvList(Pageable pageable, String subject, String search, String name);

    //    대기중인 것 만
    @Transactional
    Page<ApprovalDto> apvWaitList(Pageable pageable, String subject
        , Long approvalStatusEntity_Id, String search, String name);

     //  반려된 것만
    @Transactional
    Page<ApprovalDto> apvDenyList(Pageable pageable, String subject
        , Long approvalStatusEntity_Id, String search, String name);

    //내가 작성한 보고서
    @Transactional
    Page<ApprovalDto> myApvList(Pageable pageable, String subject, String search, Long memberId);


    ApprovalDto apvDetail(Long id);

    void apvDeleteById(Long id);

    void apvOk(ApprovalDto approvalDto);

    void apvUpdate(ApprovalDto approvalDto) throws IOException;


    Long apvCount(String name);

    Long apvWaitCount(String name, Long approvalStatusEntity_Id);

    Long apvMyCount(Long memberId);

    Long apvDenyCount(String name, Long approvalStatusEntityId);

    Page<ApprovalDto> myApvDenyList(Pageable pageable, String subject, String search, Long memberId, Long approvalStatusEntity_Id);

    Long apvMyDenyCount(Long memberId,  Long approvalStatusEntity_Id);

    String getDepartmentByApvFnlName(Long id);

    String getRankByApvFnlName(Long id);
}
