package org.spring.codingStory.movieApi.movie.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "WeekDayMovie")
public class WeekDayMovieEntity {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String movieCd; //코드
    private String movieNm; // 제목
    private String openDt; // 개봉 예정일
    private String audiAcc; // 누적관객수

    @Column(name = "movie_rank")
    private int movieRank; // 박스오피스 순위

    private String showRange;
//    private String audiCnt; //해당일의 관객수
//    private String audiChange; //전일 대비 관객수 증감 비율
//    private String rankInten; //전일대비 순위의 증감분
//    private String nationAlt; // 제작 국가
//    private String genreAlt; // 영화장르(드라마, 코미디...)
//    private String repNationNm; //대표 제작국가명
//    private String repGenreNm; //대표 장르명
//    private List<Directors> directors; // 감독들
//    private List<Companys> companys;



}

