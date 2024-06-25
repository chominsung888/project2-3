package org.spring.codingStory.movieApi.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeekMovieInfoResultReson {
    private BoxOfficeResult boxOfficeResult;


    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BoxOfficeResult {
        private String boxofficeType;
        private String showRange;
        private String yearWeekTime;
        private List<WeeklyBoxOfficeList> weeklyBoxOfficeList;

        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        @Setter
        public static class WeeklyBoxOfficeList {
            private String rnum; //순번
            private int rank; //해당일자의 박스오피스 순위
            private String rankInten; //전일대비 순위의 증감분
            private String rankOldAndNew; //랭킹에 신규진입여부를 출력합니다.
            private String movieCd; //
            private String movieNm; //
            private String openDt; //  개봉 예정일
            private String salesAmt; //해당일의 매출액
            private String salesShare; //상영작의 매출총액 대비 해당 영화의 매출비율
            private String salesInten; //전일 대비 매출액 증감분
            private String salesChange; //전일 대비 매출액 증감 비율
            private String salesAcc; //누적매출액
            private String audiCnt; //해당일의 관객수
            private String audiInten; //전일 대비 관객수 증감분
            private String audiChange; //전일 대비 관객수 증감 비율
            private String audiAcc; //누적관객수
            private String scrnCnt; //해당일자에 상영한 스크린수
            private String showCnt; //해당일자에 상영된 횟수


               }
    }
}