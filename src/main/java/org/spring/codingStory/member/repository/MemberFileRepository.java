package org.spring.codingStory.member.repository;

import org.spring.codingStory.member.entity.MemberFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberFileRepository extends JpaRepository<MemberFileEntity,Long> {

  Optional<MemberFileEntity> findByMemberEntityId(Long id);
}
