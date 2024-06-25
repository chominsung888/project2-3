package org.spring.codingStory;


import org.junit.jupiter.api.Test;
import org.spring.codingStory.mRank.entity.RankEntity;
import org.spring.codingStory.mRank.repository.MRankRepository;
import org.spring.codingStory.mRank.serviceImpl.service.MRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class Mrank {
  @Autowired
  private MRankRepository mRankRepository;

  @Autowired
  private MRankService mRankService;


  @Test
  void rankInsert() {

      RankEntity rankEntity=RankEntity.builder()
              .rankName("사원")
              .build();
    RankEntity rankEntity1=RankEntity.builder()
            .rankName("팀장")
            .build();
    RankEntity rankEntity2=RankEntity.builder()
            .rankName("지점장")
            .build();
    RankEntity rankEntity3=RankEntity.builder()
            .rankName("사장")
            .build();

      mRankRepository.save(rankEntity);
      mRankRepository.save(rankEntity1);
      mRankRepository.save(rankEntity2);
      mRankRepository.save(rankEntity3);
  }


}
