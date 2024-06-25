package org.spring.codingStory.buschatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.spring.codingStory.bus.dto.BusDto;
import org.spring.codingStory.bus.entity.BusEntity;
import org.spring.codingStory.bus.repository.BusDetailRepository;
import org.spring.codingStory.bus.repository.BusRepository;
import org.spring.codingStory.buschatbot.dto.BusAnswerDto;
import org.spring.codingStory.buschatbot.dto.BusArrInfoDto;
import org.spring.codingStory.buschatbot.dto.BusMessageDto;
import org.spring.codingStory.buschatbot.entity.BusAnswer;
import org.spring.codingStory.buschatbot.entity.BusArrInfoEntity;
import org.spring.codingStory.buschatbot.entity.BusIntention;
import org.spring.codingStory.buschatbot.repository.BusArrInfoRepository;
import org.spring.codingStory.buschatbot.repository.BusChatBotIntentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BusKomoranService {

  @Autowired
  private Komoran busKomoran;

  public BusMessageDto nlpAnalyze(String message) {

    KomoranResult result = busKomoran.analyze(message);

    //문자에서 명사들만 추출한 목록 중복제거해서 set
    Set<String> nouns = result.getNouns().stream()
            .collect(Collectors.toSet());
    nouns.forEach((noun) -> {
      System.out.println(">>>:" + noun);
    });
    ;//메세지에서 명사추출

    return analyzeToken(nouns);
  }

  //입력된 목적어를 하나씩 파악하여 DB에서 검색하기위해 decisionTree()메서드로 전달
  private BusMessageDto analyzeToken(Set<String> nouns) {

    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
    BusMessageDto messageDTO = BusMessageDto.builder()
            .time(today.format(timeFormatter))//시간세팅
            .build();


    for (String token : nouns) {

      //1차의도가 존재하는지 파악
      Optional<BusIntention> result = decisionTree(token, null);

      if (result.isEmpty()) continue;//존재하지 않으면 다음토큰 검색

      //1차 토근확인시 실행
      System.out.println(">>>>1차:" + token);
      //1차목록 복사
      Set<String> next = nouns.stream().collect(Collectors.toSet());
      //목록에서 1차토큰 제거
      next.remove(token);

      //2차분석 메서드
      BusAnswerDto answer = analyzeToken(next, result).toAnswerDTO();

      //전화인경우 전화,전화번호 번호탐색
      if (token.contains("버스")) {
        BusDto bus = analyzeTokenIsBusRouteNm(next);
        answer.bus(bus);//전화인경우에만 전화 데이터
      } else if (token.contains("안녕")) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        messageDTO.today(today.format(dateFormatter));//처음 접속할때만 날짜표기
      } else if (token.contains("정류장")) {
        BusArrInfoDto busArrInfoDto=analyzeTokenIsBusStationNm(next);
        answer.busArr(busArrInfoDto);
      }

      messageDTO.answer(answer);//토근에대한 응답정보


      return messageDTO;
    }
    //분석 명사들이 등록한 의도와 일치하는게 존재하지 않을경우 null
    BusAnswerDto answer = decisionTree("기타", null).get().getAnswer().toAnswerDTO();
    messageDTO.answer(answer);//토근에대한 응답정보
    return messageDTO;
  }

  @Autowired
  private BusRepository busRepository;
  @Autowired
  private BusDetailRepository busDetailRepository;
  @Autowired
  private BusArrInfoRepository busArrInfoRepository;


  private BusDto analyzeTokenIsBusRouteNm(Set<String> next) {

    for (String name : next) {

      System.out.println(name + " <<2차");
      Optional<BusEntity> bus = busRepository.findByBusRouteNm(name);
      if (!bus.isPresent()) continue;
      //존재하면
      String busRouteNm = bus.get().getBusRouteNm();
      String stStationNm = bus.get().getStStationNm();
      String edStationNm = bus.get().getEdStationNm();
      String busRouteId = bus.get().getBusRouteId();
      String firstBusTm = bus.get().getFirstBusTm();
      String lastLowTm = bus.get().getLastLowTm();
      String term = bus.get().getTerm();
      String corpNm = bus.get().getCorpNm();
      String routeType= bus.get().getRouteType();

      return BusDto.builder()
              .busRouteNm(busRouteNm)
              .stStationNm(stStationNm)
              .edStationNm(edStationNm)
              .busRouteId(busRouteId)
              .firstBusTm(firstBusTm)
              .lastLowTm(lastLowTm)
              .term(term)
              .corpNm(corpNm)
              .routeType(routeType)
              .build();
    }
    return null;
  }

  private BusArrInfoDto analyzeTokenIsBusStationNm(Set<String> next) {

    for (String name : next) {

      System.out.println(name + " <<2차");
      Optional<BusArrInfoEntity> bus = busArrInfoRepository.findByStNm(name);

      if (!bus.isPresent()) continue;
      //존재하면
      String stNm = bus.get().getStNm();
      String arrmsg1=bus.get().getArrmsg1();
      String arrmsg2=bus.get().getArrmsg2();
      String arsId=bus.get().getArsId();
      String busRouteAbrv=bus.get().getBusRouteAbrv();
      String busRouteId=bus.get().getBusRouteId();
      String firstTm=bus.get().getFirstTm();
      String full1=bus.get().getFull1();
      String full2=bus.get().getFull2();
      String vehId1=bus.get().getVehId1();
      String vehId2=bus.get().getVehId2();
      String plainNo1=bus.get().getPlainNo1();
      String plainNo2=bus.get().getPlainNo2();
      String brdrde_Num1=bus.get().getBrdrde_Num1();
      String brdrde_Num2=bus.get().getBrdrde_Num2();

      return BusArrInfoDto.builder()
              .stNm(stNm)
              .arrmsg1(arrmsg1)
              .arrmsg2(arrmsg2)
              .arsId(arsId)
              .busRouteAbrv(busRouteAbrv)
              .busRouteId(busRouteId)
              .firstTm(firstTm)
              .full1(full1)
              .full2(full2)
              .vehId1(vehId1)
              .vehId2(vehId2)
              .plainNo1(plainNo1)
              .plainNo2(plainNo2)
              .brdrde_Num1(brdrde_Num1)
              .brdrde_Num2(brdrde_Num2)
              .build();
    }
    return null;
  }




  //1차의도가 존재하면
  //하위의도가 존재하는지 파악
  private BusAnswer analyzeToken(Set<String> next, Optional<BusIntention> upper) {

    for (String token : next) {
      // 1차의도를 부모로하는 토큰이 존재하는지 파악
      Optional<BusIntention> result = decisionTree(token, upper.get());
      if (result.isEmpty()) continue;

      System.out.println(">>>>2차:" + token);
      return result.get().getAnswer();//1차-2차 존재하는경우 답변
    }
    return upper.get().getAnswer();//1차만 존재하는 답변
  }




  @Autowired
  private BusChatBotIntentionRepository intention;

  //의도가 존재하는지 DB에서 파악
  // 안녕 -> 등록
  private Optional<BusIntention> decisionTree(String token, BusIntention upper) {
    return intention.findByNameAndUpper(token, upper);
  }






}
