package org.spring.codingStory.weatherChatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.spring.codingStory.weatherApi.weather.entity.WeatherEntity;
import org.spring.codingStory.weatherApi.weather.repository.WeatherRepository;
import org.spring.codingStory.weatherChatbot.dto.TempInfo;
import org.spring.codingStory.weatherChatbot.dto.WeatherAnswerDTO;
import org.spring.codingStory.weatherChatbot.dto.WeatherMessageDTO;
import org.spring.codingStory.weatherChatbot.entity.WeatherAnswer;
import org.spring.codingStory.weatherChatbot.entity.WeatherChatBotIntention;
import org.spring.codingStory.weatherChatbot.repository.WeatherChatBotIntentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WeatherKomoranService {


  // 도시 이름 매핑
  private static final Map<String, String> CITY_NAME_MAP = new HashMap<>();
  static {
    CITY_NAME_MAP.put("야외", "Seoul");
    CITY_NAME_MAP.put("본점", "Samjeon-dong");
    CITY_NAME_MAP.put("노원", "Guri-si");
    CITY_NAME_MAP.put("자동차관", "Munsan");
    CITY_NAME_MAP.put("커플", "Yongsan");
    // 필요한 다른 도시 이름 매핑 추가
  }



  @Autowired
  private Komoran komoran;

  public WeatherMessageDTO nlpAnalyze(String message) {

    KomoranResult result = komoran.analyze(message);

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
  private WeatherMessageDTO analyzeToken(Set<String> nouns) {

    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
    WeatherMessageDTO weatherMessageDTO = WeatherMessageDTO.builder()
            .time(today.format(timeFormatter))//시간세팅
            .build();

    for (String token : nouns) {

      //1차의도가 존재하는지 파악
      Optional<WeatherChatBotIntention> result = decisionTree(token, null);

      if (result.isEmpty()) continue;//존재하지 않으면 다음토큰 검색

      //1차 토근확인시 실행
      System.out.println(">>>>1st check:" + token);
      //1차목록 복사
      Set<String> next = nouns.stream().collect(Collectors.toSet());
      //목록에서 1차토큰 제거
      next.remove(token);

      //2차분석 메서드
      WeatherAnswerDTO answer = analyzeToken(next, result).toAnswerDTO();

      //전화인경우 전화,전화번호 번호탐색
      if (token.contains("전화")) {

      }

      else if (token.contains("날씨")) {

        System.out.println("check1");
        TempInfo temp = analyzeTokenIsTemp(next);

        System.out.println("check2");
        answer.temp(temp);//전화인경우에만 전화 데이터

      }

      else if (token.contains("안녕")) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy(년) MM(월) dd(일)");
        weatherMessageDTO.today(today.format(dateFormatter));//처음 접속할때만 날짜표기
      }

      weatherMessageDTO.answer(answer);//토근에대한 응답정보

      return weatherMessageDTO;
    }
    //분석 명사들이 등록한 의도와 일치하는게 존재하지 않을경우 null
    WeatherAnswerDTO answer = decisionTree("기타", null).get().getWeatherAnswer().toAnswerDTO();
    weatherMessageDTO.answer(answer);//토근에대한 응답정보
    return weatherMessageDTO;
  }

  ////////////////////////////////////////////////////////////////////////////////////

  @Autowired
  private WeatherRepository weatherRepository;


  ////////////////////////////////////


  //날씨 문의인경우 DB에서 지역을 찾아서 처리
  private TempInfo analyzeTokenIsTemp(Set<String> next) {


    System.out.println("check3");
    for (String name : next) {
      // 입력된 name 값을 매핑된 영어 이름으로 변환
      String englishName = CITY_NAME_MAP.getOrDefault(name, name).trim();
      System.out.println(englishName + " <<2nd check 2");

      Optional<WeatherEntity> weather = weatherRepository.findByName(englishName);
      if (!weather.isPresent()) {
        System.out.println(englishName + " not found in the database");
        continue;
      }
      // 존재하면
      String temp = weather.get().getTemp();
      String weatherName = weather.get().getName();
      String tempMax=weather.get().getTemp_max();

      System.out.println("Found weather info: " + weatherName + " with temp: " + temp + " with temp_max" + tempMax);

      return TempInfo.builder()
              .temp(temp)
              .weatherName(weatherName)
              .tempMax(tempMax)
              .build();
    }
    System.out.println("check5");
    return null;
  }


  ////////////////////////////////////////////////////////////////////////////////////


  //1차의도가 존재하면
  //하위의도가 존재하는지 파악
  private WeatherAnswer analyzeToken(Set<String> next, Optional<WeatherChatBotIntention> upper) {

    for (String token : next) {
      // 1차의도를 부모로하는 토큰이 존재하는지 파악
      Optional<WeatherChatBotIntention> result = decisionTree(token, upper.get());
      if (result.isEmpty()) continue;

      System.out.println(">>>>2nd check:" + token);
      return result.get().getWeatherAnswer();//1차-2차 존재하는경우 답변
    }
    return upper.get().getWeatherAnswer();//1차만 존재하는 답변
  }

  @Autowired
  private WeatherChatBotIntentionRepository intention;

  //의도가 존재하는지 DB에서 파악
  // 안녕 -> 등록
  private Optional<WeatherChatBotIntention> decisionTree(String token, WeatherChatBotIntention upper) {
    return intention.findByNameAndUpper(token, upper);
  }


}
