package org.spring.codingStory.bus.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.codingStory.bus.busStation.BusStationResponse;
import org.spring.codingStory.bus.busStation.ItemStationList;
import org.spring.codingStory.bus.data.BusResponse;
import org.spring.codingStory.bus.data.ItemList;
import org.spring.codingStory.bus.dto.BusDto;
import org.spring.codingStory.bus.entity.BusDetailEntity;
import org.spring.codingStory.bus.entity.BusEntity;
import org.spring.codingStory.bus.repository.BusDetailRepository;
import org.spring.codingStory.bus.repository.BusRepository;
import org.spring.codingStory.buschatbot.busArrInfo.BusArrInfoResponse;
import org.spring.codingStory.buschatbot.busArrInfo.ItemArrList;
import org.spring.codingStory.buschatbot.entity.BusArrInfoEntity;
import org.spring.codingStory.buschatbot.repository.BusArrInfoRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusService {

  private final BusRepository busRepository;
  private final BusDetailRepository busDetailRepository;
  private final BusArrInfoRepository busArrInfoRepository;


//
//  @Autowired
//  private BusService busService;


  public void insertResposeBody(String rs) {

    ObjectMapper objectMapper = new ObjectMapper();
    BusResponse response = null;

    try {
      // json 문자열데이터를 -> 클래스에 매핑
      response = objectMapper.readValue(rs, BusResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    List<ItemList> busItemList = response.getMsgBody().getItemList()
        .stream()
        .collect(Collectors.toList());

    System.out.println(" <<  busItemList " + busItemList);

    for (ItemList item : busItemList) {
      System.out.println("  busRouteId " + item.getBusRouteId());
      BusEntity busEntity = BusEntity.builder()
          .busRouteId(item.getBusRouteId())
          .busRouteNm(item.getBusRouteNm())
          .firstBusTm(item.getFirstBusTm())
          .lastLowTm(item.getLastLowTm())
          .term(item.getTerm())
          .corpNm(item.getCorpNm())
          .routeType(item.getRouteType())
          .edStationNm(item.getEdStationNm())
          .stStationNm(item.getStStationNm())
          .build();

      Optional<BusEntity> optionalBusEntity
          = busRepository.findByBusRouteId(item.getBusRouteId());

      if (!optionalBusEntity.isPresent()) {
        busRepository.save(busEntity);
      }
    }
  }

  public void busStationPostdo(String rs) {

    System.out.println(rs + " rs2");
    ObjectMapper objectMapper = new ObjectMapper();
    BusStationResponse response = null;

    try {
      // json 문자열데이터를 -> 클래스에 매핑
      response = objectMapper.readValue(rs, BusStationResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(" <<  BusStationResponse " + response);

    List<ItemStationList> busStationItemList = response.getMsgBody().getItemList()
        .stream()
        .collect(Collectors.toList());

    System.out.println(" <<  busStationItemList " + busStationItemList);

    for (ItemStationList item : busStationItemList) {
      System.out.println("  getStationNm " + item.getStationNm());

      BusDetailEntity busDetailEntity = BusDetailEntity.builder()
          .busRouteId(item.getBusRouteId())
          .beginTm(item.getBeginTm())
          .lastTm(item.getLastTm())
          .busRouteNm(item.getBusRouteNm())
          .routeType(item.getRouteType())
          .gpsX(item.getGpsX())
          .gpsY(item.getGpsY())
          .posX(item.getPosX())
          .posY(item.getPosY())
          .stationNm(item.getStationNm())
          .stationNo(item.getStationNo())
          .build();

      Optional<BusDetailEntity> optionalBusDetailEntity
          = busDetailRepository.findByBusRouteId(item.getBusRouteId());

      if (!optionalBusDetailEntity.isPresent()) {

        System.out.println(" save+");
        busDetailRepository.save(busDetailEntity);

      }
    }
  }

  private static final String CSV_FILE = "resources/templates/bus/서울특별시_버스노선별_정류장별_시간대별_승하차_인원_정보_20220603.csv";

/*  public List<String[]> readCsv() throws IOException, CsvValidationException {
    List<String[]> data = new ArrayList<>();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CSV_FILE);
    if (inputStream == null) {
      throw new FileNotFoundException("CSV file not found: " + CSV_FILE);
    }
    try (Reader reader = new InputStreamReader(inputStream);
         CSVReader csvReader = new CSVReader(reader)) {
      String[] line;
      while ((line = csvReader.readNext()) != null) {
        data.add(line);
      }
    }
    return data;
  }*/


  public List<BusDto> busList(String busRouteId) {

    List<BusEntity> busEntities = busRepository.findAll();
//    List<BusEntity> busEntities = busRepository.findAllByBusRouteIdContains(busRouteId);


    List<BusDto> busDtos = new ArrayList<>();

    for (BusEntity busInfo : busEntities) {
      BusDto busDto1 = BusDto.toSelectBusInfo(busInfo);
      busDtos.add(busDto1);
    }

    return busDtos;
  }

  public String getBusRouteInfo(String serviceKey, String stSrch) {
    try {
      // 버스 노선 정보를 조회하는 API URL
      String apiUrl = "http://ws.bus.go.kr/api/rest/pathinfo/getLocationInfo";

      // API 호출을 위한 파라미터 설정
      StringBuilder urlBuilder = new StringBuilder(apiUrl);
      urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
      urlBuilder.append("&" + URLEncoder.encode("stSrch", "UTF-8") + "=" + URLEncoder.encode(stSrch, "UTF-8"));

      // URL 객체 생성
      URL url = new URL(urlBuilder.toString());

      // HTTP 연결 설정
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "application/json");

      // 응답 코드 확인
      int responseCode = conn.getResponseCode();
      System.out.println("Response code: " + responseCode);

      // API 호출 결과 읽기
      BufferedReader rd;
      if (responseCode >= 200 && responseCode <= 300) {
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else {
        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }

      StringBuilder response = new StringBuilder();
      String line;
      while ((line = rd.readLine()) != null) {
        response.append(line);
      }

      // 자원 정리
      rd.close();
      conn.disconnect();

      return response.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return "Error occurred: " + e.getMessage();
    }
  }

  public void busArrInfo(String rs) {

    System.out.println(rs + " rs2");
    ObjectMapper objectMapper = new ObjectMapper();
    BusArrInfoResponse response = null;

    try {
      // json 문자열데이터를 -> 클래스에 매핑
      response = objectMapper.readValue(rs, BusArrInfoResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(" <<  BusStationResponse " + response);

    List<ItemArrList> itemArrList = response.getMsgBody().getItemList()
            .stream()
            .collect(Collectors.toList());

    System.out.println(" <<  busStationItemList " + itemArrList);

    for (ItemArrList item : itemArrList) {

      BusArrInfoEntity busArrInfoEntity1 = BusArrInfoEntity.builder()
              .arrmsg1(item.getArrmsg1())
              .arrmsg2(item.getArrmsg2())
              .arsId(item.getArsId())
              .brdrde_Num1(item.getBrdrde_Num1())
              .brdrde_Num2(item.getBrdrde_Num2())
              .busRouteAbrv(item.getBusRouteAbrv())
              .busRouteId(item.getBusRouteId())
              .dir(item.getDir())
              .firstTm(item.getFirstTm())
              .full1(item.getFull1())
              .full2(item.getFull2())
              .lastTm(item.getLastTm())
              .mkTm(item.getMkTm())
              .stId(item.getStId())
              .stNm(item.getStNm())
              .term(item.getTerm())
              .vehId1(item.getVehId1())
              .vehId2(item.getVehId2())
              .plainNo1(item.getPlainNo1())
              .plainNo2(item.getPlainNo2())
              .build();

//      List<BusArrInfoEntity> arrInfo=busArrInfoRepository.findAllByStNm(item.getStNm());

//      if(arrInfo.isEmpty()) {

//      busArrInfoRepository.save(busArrInfoEntity1);
//      }


      Optional<BusArrInfoEntity> optionalBusArrInfoEntity1
              = busArrInfoRepository.findAllByStNm(item.getStNm());

      if (!optionalBusArrInfoEntity1.isPresent()) {
        busArrInfoRepository.save(busArrInfoEntity1);
      }

    }

  }




}