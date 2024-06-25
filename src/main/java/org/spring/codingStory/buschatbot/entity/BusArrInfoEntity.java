package org.spring.codingStory.buschatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity(name = "bus_arr")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusArrInfoEntity {


  @Id
  @Column(name = "bus_arr_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
