package org.spring.codingStory.bus.data;

import lombok.Data;

@Data
public class ItemList {

  private String busRouteId;
  private String busRouteNm;
  private String busRouteAbrv;
  private String length;
  private String routeType;
  private String stStationNm;
  private String edStationNm;
  private String term;
  private String lastBusYn;
  private String firstBusTm;
  private String lastBusTm;
  private String lastLowTm;
  private String firstLowTm;
  private String corpNm;
}
