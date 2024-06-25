package org.spring.codingStory.buschatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusArrInfoDto {

  private Long id;
  private String arrmsg1;
  private String arrmsg2;
  private String arsId;
  private String brdrde_Num1;
  private String brdrde_Num2;
  private String busRouteAbrv;
  private String busRouteId;
  private String dir;
  private String firstTm;
  private String full1;
  private String full2;
  private String lastTm;
  private String mkTm;
  private String stId;
  private String stNm;
  private String term;
  private String vehId1;
  private String vehId2;
  private String plainNo1;
  private String plainNo2;

//  public static BusArrInfoDto toSelect(BusArrInfoEntity arrEntity) {
//    BusArrInfoDto busArrInfoDtos=new BusArrInfoDto();
//
//    busArrInfoDtos.setBusRouteId(arrEntity.getBusRouteId());
//    busArrInfoDtos.setStNm(arrEntity.getStNm());
//
//    return busArrInfoDtos;
//
//
//
//
//  }
}
