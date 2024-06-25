package org.spring.codingStory.buschatbot.controller;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.spring.codingStory.bus.repository.BusDetailRepository;
import org.spring.codingStory.bus.repository.BusRepository;
import org.spring.codingStory.buschatbot.repository.BusArrInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class BusKomoranConfig {

  private String BUS_DIC="bus.dic";

  @Autowired
  BusRepository bus;

  @Autowired
  BusDetailRepository busDetail;

  @Autowired
  BusArrInfoRepository busArrInfoRepository;

  @Bean
  Komoran busKomoran() {
    busDic();

    Komoran komoran=new Komoran(DEFAULT_MODEL.LIGHT);
    komoran.setUserDic(BUS_DIC);

    return komoran;
  }

  //부서테이블(부서명), 멤버테이블(이름)
  private void busDic() {

    Set<String> keys = new HashSet<>();

    //기존에 수동으로 등록된 파일에서 고유명사만 추출
    try {
      File file=new File(BUS_DIC);
      if(file.exists()) {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data = null;
        while ((data = br.readLine()) != null) {
          if (data.startsWith("#"))//주석이 있으면 주석 제거
            continue;
          String[] str = data.split("\\t"); // 탭영역 만큼 간격 설정 이이름	NNP  영업부	NNP
          keys.add(str[0]); // set에 저장
        }
        br.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    ////////////////////////////////////////////////////
    bus.findAll().forEach(e ->{
      keys.add(e.getBusRouteNm());
    });
    bus.findAll().forEach(e ->{
      keys.add(e.getBusRouteId());
    });
    busArrInfoRepository.findAll().forEach(e->{
      keys.add(e.getStNm());
    });

    // Set에 저장된 명단을 고유명사로 파일에 등록
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(BUS_DIC));
      keys.forEach(key -> {
        try {
          bw.write(key + "\tNNP\n");
          //  이이름	NNP
          //  영업부	NNP
          System.out.println(key);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });

      bw.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }


}
