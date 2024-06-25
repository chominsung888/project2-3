package org.spring.codingStory.buschatbot.apiConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiConfig {

//  private static final  String serviceKey= "BgIFZkomLc9QPwUuSC%2BQ%2FKLGCAbkFx2OdAefKbAVgTR2X6KNK3hGYuVgRdEFjqQDcZBBGhpn6QYzVNM0uLwrsA%3D%3D";
  private static final String serviceKey = "b8W45B4pCtv6n5ViMpPNCk%2F%2F2U%2Br6RaJeayypTlQ95rjY18ZJ3MT0rLsevogI0oSPQFvdn8wT1Rw2LAqUn0I7A%3D%3D";

  public static String busArrInfo(String busRouteId) throws IOException {
    StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("busRouteId","UTF-8") + "=" + URLEncoder.encode(busRouteId, "UTF-8")); /*정류소 ID*/
    urlBuilder.append("&resultType=json");
    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
