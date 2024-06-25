package org.spring.codingStory.movieApi.movie.dto;

import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MovieInfoResultReson {
    private MovieInfoResult movieInfoResult;


    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class MovieInfoResult {
        private MovieInfo movieInfo;
        private String source;

        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        @Setter
        public static class MovieInfo {
            private String movieCd;
            private String movieNm;
            private String movieNmEn;
            private String movieNmOg;
            private String showTm;
            private String prdtYear;
            private String openDt;
            private String prdtStatNm;
            private String typeNm;
            private List<Nation> nations;
            private List<Genre> genres;
            private List<Director> directors;
            private List<Actor> actors;
            private List<ShowType> showTypes;
            private List<Company> companys;
            private List<Audit> audits;
            private List<Staff> staffs;

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Nation {
                private String nationNm;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Genre {
                private String genreNm;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Director {
                private String peopleNm;
                private String peopleNmEn;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Actor {
                private String peopleNm;
                private String peopleNmEn;
                private String cast;
                private String castEn;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class ShowType {
                private String showTypeGroupNm;
                private String showTypeNm;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Company {
                private String companyCd;
                private String companyNm;
                private String companyNmEn;
                private String companyPartNm;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Audit {
                private String auditNo;
                private String watchGradeNm;
            }

            @ToString
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Getter
            @Setter
            public static class Staff {
                private String peopleNm;
                private String peopleNmEn;
                private String staffRoleNm;
            }

            // 각 리스트들을 반환하는 메소드 추가 예시
            public List<Nation> getNations() {
                return this.nations;
            }

            public List<Genre> getGenres() {
                return this.genres;
            }

            public List<Director> getDirectors() {
                return this.directors;
            }

            public List<Actor> getActors() {
                return this.actors;
            }

            public List<ShowType> getShowTypes() {
                return this.showTypes;
            }

            public List<Company> getCompanys() {
                return this.companys;
            }

            public List<Audit> getAudits() {
                return this.audits;
            }

            public List<Staff> getStaffs() {
                return this.staffs;
            }
        }
    }
}