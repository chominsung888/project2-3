package org.spring.codingStory.approval.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.approval.dto.ApprovalStatusDto;
import org.spring.codingStory.approval.entity.ApprovalStatusEntity;
import org.spring.codingStory.approval.repository.ApprovalStatusRepository;
import org.spring.codingStory.approval.serviceImpl.service.ApprovalStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApprovalStatusServiceImpl  implements ApprovalStatusService {

    private final ApprovalStatusRepository approvalStatusRepository;

    @Override
    public List<ApprovalStatusDto> apvStatusList() {
        List<ApprovalStatusEntity> approvalStatusEntityList = null;
        approvalStatusEntityList =  approvalStatusRepository.findAll();

        List<ApprovalStatusDto> approvalStatusDtoList =approvalStatusEntityList.stream()
            .map(ApprovalStatusDto::toApvStatusList).collect(Collectors.toList());

        return approvalStatusDtoList;
    }
}
