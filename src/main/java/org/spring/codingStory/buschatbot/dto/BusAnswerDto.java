package org.spring.codingStory.buschatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.bus.dto.BusDto;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusAnswerDto {

  private long no;

  private String content;

  private String keyword;//name

  private BusDto bus;


  private BusArrInfoDto busArr;

  private List<BusDto> busDtoList;


  public BusAnswerDto bus(BusDto bus){
    this.bus=bus;
    return this;
  }

  public BusAnswerDto busArr(BusArrInfoDto busArr){
    this.busArr=busArr;
    return this;
  }





}
