package org.spring.codingStory.buschatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.bus.dto.BusDetailDto;

import java.util.List;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusDetailAnswerDto {

  private long no;

  private String content;

  private String keyword;//name

  private BusDetailDto busDetail;

  private List<BusDetailDto> busDetailDtoList;

  public BusDetailAnswerDto bus(BusDetailDto busDetail){
    this.busDetail=busDetail;
    return this;
  }

}
