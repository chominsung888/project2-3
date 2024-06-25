package org.spring.codingStory.bus.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusDetailDto {

  private Long id;

  private String busRouteId;
  private String beginTm;
  private String lastTm;
  private String busRouteNm;
  private String routeType;
  private String gpsX;
  private String gpsY;
  private String posX;
  private String posY;
  private String stationNm;
  private String stationNo;


}
