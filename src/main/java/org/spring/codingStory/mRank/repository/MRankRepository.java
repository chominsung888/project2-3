package org.spring.codingStory.mRank.repository;

import org.spring.codingStory.mRank.entity.RankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MRankRepository extends JpaRepository<RankEntity,Long> {
}
