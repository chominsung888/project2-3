package org.spring.codingStory.approval.repository;

import org.spring.codingStory.approval.entity.ApprovalStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatusEntity,Long> {
}
