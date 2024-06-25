package org.spring.codingStory.mRank.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.mRank.dto.RankDto;
import org.spring.codingStory.mRank.entity.RankEntity;
import org.spring.codingStory.mRank.repository.MRankRepository;
import org.spring.codingStory.mRank.serviceImpl.service.MRankService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MRankServiceImpl implements MRankService {


  private final MRankRepository mRankRepository;


  @Override
  public List<RankDto> findRank() {

    List<RankEntity> rankEntities = mRankRepository.findAll();

    List<RankDto> rankDtoList = new ArrayList<>();

    for (RankEntity rank : rankEntities) {
      RankDto rankDto = RankDto.toSelectRank(rank);
      rankDtoList.add(rankDto);
    }
    return rankDtoList;
  }

}



