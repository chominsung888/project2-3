package org.spring.codingStory.movieApi.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoxOfficeResult {

    private String boxofficeType;
    private String showRange;
    private List<DailyBoxOfficeList> dailyBoxOfficeList;
    private List<WeekMovieInfoResultReson.BoxOfficeResult.WeeklyBoxOfficeList> weeklyBoxOfficeLists;
}
