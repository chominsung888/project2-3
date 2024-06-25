package org.spring.codingStory.movieApi.mv_chatbot.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MovieInfo {

	private String movieNm;
	private String rank;
  private List<Actor> actor;
	private String mvOpen;
	private String mvCount;
	private String mvDirector;
	private String mvContent;
	private String genre;
	private String movieCd; //코드
	private String openDt; // 개봉 예정일
	private String audiAcc; // 누적관객수
}
