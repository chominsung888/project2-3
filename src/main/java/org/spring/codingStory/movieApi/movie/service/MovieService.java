package org.spring.codingStory.movieApi.movie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.codingStory.movieApi.movie.dto.*;
import org.spring.codingStory.movieApi.movie.entity.MovieDetailEntity;
import org.spring.codingStory.movieApi.movie.entity.MovieEntity;
import org.spring.codingStory.movieApi.movie.entity.WeekDayMovieEntity;
import org.spring.codingStory.movieApi.movie.entity.WeeklyMovieEntity;
import org.spring.codingStory.movieApi.movie.repository.MovieDetailRepository;
import org.spring.codingStory.movieApi.movie.repository.MovieRepository;
import org.spring.codingStory.movieApi.movie.repository.WeekDayMovieRepository;
import org.spring.codingStory.movieApi.movie.repository.WeeklyMovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final WeeklyMovieRepository weeklyMovieRepository;
    private final WeekDayMovieRepository weekDayMovieRepository;
    private final MovieDetailRepository movieDetailRepository;
    private final MovieApiService movieApiService;
    private final MovieInfoResultReson.MovieInfoResult.MovieInfo.Company company;

    public void saveMovie(Long movieId) {
        MovieEntity movie = movieApiService.getMovieById(movieId);
        if (movie != null) {
            movieRepository.save(movie);
        }
    }

    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<MovieEntity> getBoxOfficeMoviesWithPosters() {
        // 영화진흥원 API를 사용하여 박스 오피스 순위의 영화 목록을 가져옵니다.
        List<MovieEntity> boxOfficeMovies = getBoxOfficeMoviesFromKofic();

        // TMDB API를 사용하여 각 영화에 대한 포스터 정보를 가져와서 추가합니다.
        for (MovieEntity movie : boxOfficeMovies) {
            String posterUrl = getPosterUrlFromTmdb(movie.getMovieNm());
//            movie.setPosterUrl(posterUrl);
        }

        return boxOfficeMovies;
    }

    public List<MovieEntity> getTop10BoxOfficeMoviesWithPosters() {
        List<MovieEntity> boxOfficeMovies = new ArrayList<>();

        // 전날의 박스 오피스 순위 영화 목록을 가져옵니다.
        List<MovieEntity> koficMovies = getBoxOfficeMoviesFromKofic();

        // 상위 10개 영화만 선택하여 처리합니다.
        int count = 0;
        for (MovieEntity movie : koficMovies) {
            if (count >= 20) {
                break;
            }
            String posterUrl = getPosterUrlFromTmdb(movie.getMovieNm());
//            movie.setPosterUrl(posterUrl);
            boxOfficeMovies.add(movie);
            count++;
        }

        return boxOfficeMovies;
    }

    private List<MovieEntity> getBoxOfficeMoviesFromKofic() {
        // 실제로는 영화진흥위원회 API를 호출하여 데이터를 가져와야 합니다.

        // 이 예제에서는 더미 데이터를 반환합니다.

        List<MovieEntity> dummyData = new ArrayList<>();

        return dummyData;
    }

    private String getPosterUrlFromTmdb(String title) {
        // TMDB API를 통해 영화 제목을 기반으로 포스터 URL을 가져옵니다.
        // 실제로는 TMDB API를 호출하여 데이터를 가져와야 합니다.

        // 여기서는 더미 URL을 반환합니다.

        return "https://via.placeholder.com/500x750";
    }

    public void insertResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println( " <<< responseBody"+responseBody);
        MovieApiDayDto response = null;
        try {
            // JSON 문자열 데이터를 클래스에 매핑
            response = objectMapper.readValue(responseBody, MovieApiDayDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return; // 오류 발생 시 메서드 종료
        }
        System.out.println(" response>>>>"+response);
        if (response == null || response.getBoxOfficeResult() == null || response.getBoxOfficeResult().getDailyBoxOfficeList() == null) {
            System.out.println("Response or one of its components is null");
            return;
        }
        List<DailyBoxOfficeList> movieList = response.getBoxOfficeResult().getDailyBoxOfficeList();
        System.out.println(" movieList>>>>"+movieList  );
        // DB 테이블 필드에 맞추어 설정
        for (DailyBoxOfficeList el : movieList) {
            System.out.println("Processing movie: " + el.getMovieNm());

            if (el.getMovieCd() == null) {
                System.out.println("MovieCd is null for movie: " + el.getMovieNm());
                continue;
            }
            Optional<MovieEntity> optionalMovieEntity = movieRepository.findByMovieCd(el.getMovieCd());
            System.out.println("MovieEntity search completed");

            if (!optionalMovieEntity.isPresent()) {
                MovieEntity movieEntity = MovieEntity.builder()
                    .movieCd(el.getMovieCd())
                    .movieNm(el.getMovieNm())
                    .openDt(el.getOpenDt())
                    .audiAcc(el.getAudiAcc())
                    .movieRank(el.getRank())
                    .build();

                movieRepository.save(movieEntity);
            }
        }
    }

    // 주말 박스 오피스 저장
    public void insertWeeklyResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println( " <<< responseBody"+responseBody);

        WeekMovieInfoResultReson response = null;
        try {
            // JSON 문자열 데이터를 클래스에 매핑
            response = objectMapper.readValue(responseBody, WeekMovieInfoResultReson.class);
        } catch (Exception e) {
            e.printStackTrace();
            return; // 오류 발생 시 메서드 종료
        }
        System.out.println(" response>>>>"+response);

        if (response == null || response.getBoxOfficeResult() == null || response.getBoxOfficeResult().getWeeklyBoxOfficeList() == null) {
            System.out.println("Response or one of its components is null");
            return;
        }

        List<WeekMovieInfoResultReson.BoxOfficeResult.WeeklyBoxOfficeList> movieList = response.getBoxOfficeResult().getWeeklyBoxOfficeList();
        System.out.println(" movieList>>>>"+movieList  );

        // DB 테이블 필드에 맞추어 설정
        for (WeekMovieInfoResultReson.BoxOfficeResult.WeeklyBoxOfficeList el : movieList) {
            System.out.println("Processing movie: " + el.getMovieNm());

            if (el.getMovieCd() == null) {
                System.out.println("MovieCd is null for movie: " + el.getMovieNm());
                continue;
            }

            Optional<WeeklyMovieEntity> optionalWeeklyMovieEntity = weeklyMovieRepository.findByMovieCd(el.getMovieCd());
            System.out.println("WeeklyMovieEntity search completed");

            if (!optionalWeeklyMovieEntity.isPresent()) {
                WeeklyMovieEntity weeklyMovieEntity = WeeklyMovieEntity.builder()
                    .movieCd(el.getMovieCd())
                    .movieNm(el.getMovieNm())
                    .openDt(el.getOpenDt())
                    .audiAcc(el.getAudiAcc())
                    .movieRank(el.getRank())
                    .build();

                weeklyMovieRepository.save(weeklyMovieEntity);
            }
        }
    }

        //평일(월~목) 데이터베이스 저장
    public void insertWeekDayResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println( " <<< responseBody"+responseBody);

        WeekMovieInfoResultReson response = null;
        try {
            // JSON 문자열 데이터를 클래스에 매핑
            response = objectMapper.readValue(responseBody, WeekMovieInfoResultReson.class);
        } catch (Exception e) {
            e.printStackTrace();
            return; // 오류 발생 시 메서드 종료
        }
        System.out.println(" response>>>>"+response);

        if (response == null || response.getBoxOfficeResult() == null || response.getBoxOfficeResult().getWeeklyBoxOfficeList() == null) {
            System.out.println("Response or one of its components is null");
            return;
        }

        List<WeekMovieInfoResultReson.BoxOfficeResult.WeeklyBoxOfficeList> movieList = response.getBoxOfficeResult().getWeeklyBoxOfficeList();
        System.out.println(" movieList>>>>"+movieList  );

        // DB 테이블 필드에 맞추어 설정
        for (WeekMovieInfoResultReson.BoxOfficeResult.WeeklyBoxOfficeList el : movieList) {
            System.out.println("Processing movie: " + el.getMovieNm());

            if (el.getMovieCd() == null) {
                System.out.println("MovieCd is null for movie: " + el.getMovieNm());
                continue;
            }

            Optional<WeekDayMovieEntity> optionalWeekDayMovieEntity = weekDayMovieRepository.findByMovieCd(el.getMovieCd());
            System.out.println("WeeklyMovieEntity search completed");

            if (!optionalWeekDayMovieEntity.isPresent()) {
                WeekDayMovieEntity weekDayMovieEntity = WeekDayMovieEntity.builder()
                    .movieCd(el.getMovieCd())
                    .movieNm(el.getMovieNm())
                    .openDt(el.getOpenDt())
                    .audiAcc(el.getAudiAcc())
                    .movieRank(el.getRank())
                    .build();

                weekDayMovieRepository.save(weekDayMovieEntity);
            }
        }

    }


    // 장르 매핑
    private List<Genre> mapGenres(List<Genre> genres) {
        return genres.stream()
                .map(genre -> {
                    Genre genreEntity = new Genre();
                    genreEntity.setGenreNm(genre.getGenreNm());
                    return genreEntity;
                })
                .collect(Collectors.toList());
    }

    // 감독 매핑
    private List<Director> mapDirectors(List<Director> directors) {
        return directors.stream()
                .map(director -> {
                    Director directorEntity = new Director();
                    directorEntity.setPeopleNm(director.getPeopleNm());
                    return directorEntity;
                })
                .collect(Collectors.toList());
    }

    // 배우 매핑
    private List<Actor> mapActors(List<Actor> actors) {
        return actors.stream()
                .map(actor -> {
                    Actor actorEntity = new Actor();
                    actorEntity.setPeopleNm(actor.getPeopleNm());
                    actorEntity.setCast(actor.getCast());
                    return actorEntity;
                })
                .collect(Collectors.toList());
    }

    //영화 상세 정보 저장하기
    public void movieInfoResultResonBody(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(responseBody+" responseBody2");// 2
        MovieInfoResultReson response = null;
        try {
            // json 문자열 데이터를 클래스에 매핑
            response = objectMapper.readValue(responseBody, MovieInfoResultReson.class);
        } catch (Exception e) {
            e.printStackTrace();
            return; // 오류 발생 시 메서드 종료
        }
        System.out.println( response+">>>>>response3"); //3

        List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Actor> actors = response.getMovieInfoResult().getMovieInfo().getActors();
        List<String> actorNames = actors.stream()
            .map(MovieInfoResultReson.MovieInfoResult.MovieInfo.Actor::getPeopleNm)
            .collect(Collectors.toList());

        List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Director> directors=response.getMovieInfoResult().getMovieInfo().getDirectors();
        List<String> directorsName=directors.stream()
            .map(MovieInfoResultReson.MovieInfoResult.MovieInfo.Director::getPeopleNm)
            .collect(Collectors.toList());

        List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Company> companies=response.getMovieInfoResult().getMovieInfo().getCompanys();
        List<String> companyNm=companies.stream()
            .map(MovieInfoResultReson.MovieInfoResult.MovieInfo.Company::getCompanyNm)
            .collect(Collectors.toList());

        List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Genre> genres=response.getMovieInfoResult().getMovieInfo().getGenres();
        List<String> genreNm=genres.stream()
            .map(MovieInfoResultReson.MovieInfoResult.MovieInfo.Genre::getGenreNm)
            .collect(Collectors.toList());

        List<MovieInfoResultReson.MovieInfoResult.MovieInfo.ShowType> showTypes=response.getMovieInfoResult().getMovieInfo().getShowTypes();
        List<String> showTypeNm=showTypes.stream()
            .map(MovieInfoResultReson.MovieInfoResult.MovieInfo.ShowType::getShowTypeNm)
            .collect(Collectors.toList());

        Optional<MovieDetailEntity> optionalMovieDetailEntity
            = movieDetailRepository.findByMovieNm(response.getMovieInfoResult().getMovieInfo().getMovieNm());

        if (!optionalMovieDetailEntity.isPresent()) {
            // 배우 정보 리스트를 직렬화하여 문자열로 변환
            String serializedActors = actors.stream()
                .map(actor -> actor.getPeopleNm())
                .collect(joining(", "));

            String serializedDirectors=directors.stream()
                .map(director -> director.getPeopleNm())
                .collect(joining(", "));

            String serializedCompanies=companies.stream()
                .map(company -> company.getCompanyNm())
                .collect(joining(", "));

            String serializedGenres=genres.stream()
                .map(genre -> genre.getGenreNm())
                .collect(joining(", "));

            String serializedShowTypes=showTypes.stream()
                .map(showType -> showType.getShowTypeNm())
                .collect(joining(", "));

            MovieDetailEntity movieDetailEntity = MovieDetailEntity.builder()
                .movieCd(response.getMovieInfoResult().getMovieInfo().getMovieCd())
                .movieNm(response.getMovieInfoResult().getMovieInfo().getMovieNm())
                .openDt(response.getMovieInfoResult().getMovieInfo().getOpenDt())
                .prdtStatNm(response.getMovieInfoResult().getMovieInfo().getPrdtStatNm())
                .showTm(response.getMovieInfoResult().getMovieInfo().getShowTm())
                .typeNm(response.getMovieInfoResult().getMovieInfo().getTypeNm())
                .companyNm(serializedCompanies)
                .peopleNm(serializedActors)
                .showTypeNm(serializedShowTypes)
                .genreNm(serializedGenres)
                .peopleDirectorNm(serializedDirectors)
                .build();
            joining(",");
            movieDetailRepository.save(movieDetailEntity);
        }
        System.out.println(" << response " + response);
    }

    public List<MovieEntity> findTop10ByOrderByRankAsc() {
        return movieRepository.findTop10ByOrderByMovieRankAsc(); // 변경된 메서드명에 맞춰 호출
    }


}