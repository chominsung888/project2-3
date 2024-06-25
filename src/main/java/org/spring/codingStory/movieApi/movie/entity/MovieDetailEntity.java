package org.spring.codingStory.movieApi.movie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "movie_detail")
public class MovieDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieCd;
    private String movieNm;
    private String openDt;
    private String showTm;
    private String prdtStatNm;
    private String typeNm;
    private String peopleNm; // 배우이름
    private String companyNm; //제작사명
    private String showTypeNm;
    private String genreNm;
    private String peopleDirectorNm; //영화감독명


}

