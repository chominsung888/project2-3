package org.spring.codingStory.approval.repository;

import org.spring.codingStory.approval.entity.ApprovalDivEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalDivRepository extends JpaRepository<ApprovalDivEntity,Long> {
}
