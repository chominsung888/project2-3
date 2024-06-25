package org.spring.codingStory.approval.repository;

import org.spring.codingStory.approval.entity.ApprovalFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalFileRepository extends JpaRepository<ApprovalFileEntity ,Long> {


    Optional<ApprovalFileEntity> findByApprovalEntityId(Long id);
}
