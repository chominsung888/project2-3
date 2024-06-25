package org.spring.codingStory.bus.ApiExplorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Log4j2
@RequiredArgsConstructor
public class ApiExplorer {



  private static final String serviceKey = "BgIFZkomLc9QPwUuSC%2BQ%2FKLGCAbkFx2OdAefKbAVgTR2X6KNK3hGYuVgRdEFjqQDcZBBGhpn6QYzVNM0uLwrsA%3D%3D";


  //버스 노선 검색 -> 공공데이터 포털 제공 API
  public static String getBusList(String strSrch) throws IOException {

    String apiURL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";  // 버스 목록
    StringBuilder urlBuilder = new StringBuilder(apiURL); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("strSrch", "UTF-8") + "=" + URLEncoder.encode(strSrch, "UTF-8")); /**/
    urlBuilder.append("&resultType=json");

    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());


    BufferedReader rd;
    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();
    conn.disconnect();
    System.out.println(sb.toString());

    return sb.toString();
  }

  //버스 노선에 해당하는 정류장 -> 공공데이터 포털 제공 API
  public static String getResponse(String busRouteId) throws IOException {
    String apiURL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute"; // 버스노선에 해당하는 정류장
    StringBuilder urlBuilder = new StringBuilder(apiURL); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("busRouteId", "UTF-8") + "=" + URLEncoder.encode(busRouteId, "UTF-8")); /**/
    urlBuilder.append("&resultType=json");
    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();
    conn.disconnect();
    System.out.println(sb.toString());
    return sb.toString();
  }


  //버스 도착 정보
  public static String fetchBusArrivalInfo(String busRouteId) throws IOException {
                                                //http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute

    System.out.println(busRouteId+"   << busRouteId");
    log.info("==============="+busRouteId+"==========busRouteId============");

    //SELECT bus_route_id FROM groupairteam.bus where st_station_nm='7단지영업소';
    // 정류장 이름 -> 버스 아이디



    StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll");
    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")+ "=" + serviceKey); // Service Key
    urlBuilder.append("&" + URLEncoder.encode("busRouteId", "UTF-8") + "=" + URLEncoder.encode(busRouteId, "UTF-8")); // 노선ID
    urlBuilder.append("&resultType=json");
    System.out.println(urlBuilder+"<><><><><><><><><");
    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }
    rd.close();
    conn.disconnect();
    System.out.println(sb.toString());
    return sb.toString();
  }



  }



