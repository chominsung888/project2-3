package org.spring.codingStory.movieApi.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.spring.codingStory.movieApi.movie.entity.MovieEntity;
import org.spring.codingStory.movieApi.movie.data.ApiProperties;
import org.spring.codingStory.movieApi.movie.service.MovieService;
import org.spring.codingStory.movieApi.util.OpenApiUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    private final RestTemplate restTemplate; // RestTemplate 추가

    private final ApiProperties apiProperties;

    // 영화 목록 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<MovieEntity>> getAllMovies() {
        List<MovieEntity> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // 영화 ID로 영화 추가
    @PostMapping("/addById")
    public ResponseEntity<String> addMovieById(@RequestParam Long movieId) {
        movieService.saveMovie(movieId);
        return new ResponseEntity<>("Movie added successfully", HttpStatus.CREATED);
    }

    // 영화 추가
    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestBody MovieEntity movie) {
        movieService.saveMovie(movie.getId());
        return new ResponseEntity<>("Movie added successfully", HttpStatus.CREATED);
    }

    // 전날 박스오피스 데이터 가져오기
    @GetMapping("/boxoffice")
    @ResponseBody
    public ResponseEntity<String> fetchBoxOffice() {

        String koficApiKey = apiProperties.getKofic();
        String tmdbApiKey = apiProperties.getTmdb();
        // 어제의 날짜를 계산
        // 아래는 Date 클래스를 사용한 코드이므로, 보다 최신의 날짜 및 시간 처리를 위해서는
        // Java 8의 LocalDate 및 LocalDateTime을 사용하는 것이 좋습니다.
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24)); // 어제 날짜로 설정

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String targetDate = dateFormat.format(yesterday); // 어제의 날짜를 문자열로 변환하여 설정

        // 영화진흥위원회 API를 통해 어제의 박스오피스 목록을 가져오는 URL
        String koficApiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=" + koficApiKey + "&targetDt=" + targetDate;
        System.out.println(koficApiUrl+"<<<<<<koficApiUrl");
        // 영화진흥위원회 API를 통해 어제의 박스오피스 목록을 가져옵니다.
        ResponseEntity<String> koficResponseEntity = restTemplate.getForEntity(koficApiUrl, String.class);
        String koficResponse = koficResponseEntity.getBody();

        // 여기에 해당 로직을 추가하셔서 TMDB API를 호출하고 포스터 정보를 가져오세요.
        // 추출한 영화의 이름과 개봉 연도를 사용하여 TMDB API를 호출하고 포스터 정보를 가져오는 로직을 추가하세요.

        // 이 코드는 임시 코드입니다. 실제 TMDB API 호출 및 포스터 정보 가져오는 로직을 추가해야 합니다.
        String tmdbApiUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + tmdbApiKey + "&query=example_movie_title";
        ResponseEntity<String> tmdbResponseEntity = restTemplate.getForEntity(tmdbApiUrl, String.class);
        String tmdbResponse = tmdbResponseEntity.getBody();

        // 여기서 tmdbResponse를 파싱하여 포스터 URL을 추출하고 이를 이용하여 영화 정보를 저장하거나 사용할 수 있습니다.

        return new ResponseEntity<>("Box office data fetched successfully", HttpStatus.OK);
    }


    // 일일 박스오피스 데이터베이스에 저장
    @GetMapping("/movieList")
    public Map<String, String> movieList() throws JsonProcessingException {
        // 전날 날짜 계산
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = yesterday.format(formatter); // targetDt는 YYYYMMDD 형식

        String koficApiKey = "a00c456adf2ded3c39df93f44cf40503";
        System.out.println(koficApiKey + " koficApiKey");

        String tmdbApiKey = "ba65bca7bd473078269aa9976068045a";
        System.out.println(tmdbApiKey + " tmdbApiKey");

        String itemPerPage = "10";
        String movieSearchJSON = "boxoffice/searchDailyBoxOfficeList.json";
        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/"
            + movieSearchJSON + "?key=" + koficApiKey + "&targetDt=" + targetDt;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);
        System.out.println(responseBody + " responseBody");

        // JSON -> DB
        movieService.insertResponseBody(responseBody);
        System.out.println(responseBody + " responseBody processed");

        Map<String, String> movie = new HashMap<>();
        movie.put("movie", responseBody);
        return movie;
    }

    // 주간. 주말 박스오피스 불러오기
    @GetMapping("/weekBoxoffice")
    @ResponseBody
    public ResponseEntity<String> fetchWeekBoxOffice() {

        String koficApiKey = apiProperties.getKofic();
        String tmdbApiKey = apiProperties.getTmdb();
        // 어제의 날짜를 계산
        // 아래는 Date 클래스를 사용한 코드이므로, 보다 최신의 날짜 및 시간 처리를 위해서는
        // Java 8의 LocalDate 및 LocalDateTime을 사용하는 것이 좋습니다.
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24)); // 어제 날짜로 설정

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String targetDate = dateFormat.format(yesterday); // 어제의 날짜를 문자열로 변환하여 설정

        // 영화진흥위원회 API를 통해 어제의 박스오피스 목록을 가져오는 URL
        String koficApiUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=" + koficApiKey + "&targetDt=" + targetDate;
        System.out.println(koficApiUrl+"<<<<<<<<<<<<<<<koficApiUrl"); //key 값이 null이 뜬다

        // 영화진흥위원회 API를 통해 어제의 박스오피스 목록을 가져옵니다.
        ResponseEntity<String> koficResponseEntity = restTemplate.getForEntity(koficApiUrl, String.class);
        String koficResponse = koficResponseEntity.getBody();

        // 여기에 해당 로직을 추가하셔서 TMDB API를 호출하고 포스터 정보를 가져오세요.
        // 추출한 영화의 이름과 개봉 연도를 사용하여 TMDB API를 호출하고 포스터 정보를 가져오는 로직을 추가하세요.

        // 이 코드는 임시 코드입니다. 실제 TMDB API 호출 및 포스터 정보 가져오는 로직을 추가해야 합니다.
        String tmdbApiUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + tmdbApiKey + "&query=example_movie_title";
        ResponseEntity<String> tmdbResponseEntity = restTemplate.getForEntity(tmdbApiUrl, String.class);
        String tmdbResponse = tmdbResponseEntity.getBody();

        // 여기서 tmdbResponse를 파싱하여 포스터 URL을 추출하고 이를 이용하여 영화 정보를 저장하거나 사용할 수 있습니다.

        return new ResponseEntity<>("Week Box office data fetched successfully", HttpStatus.OK);
    }

    // 주말  데이터베이스에 저장
    @GetMapping("/WeekMovieList")
    public Map<String, String> weekmovieList() throws JsonProcessingException {
        // 전날 날짜 계산
        LocalDate yesterday = LocalDate.now().minusWeeks(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = yesterday.format(formatter); // targetDt는 YYYYMMDD 형식

        String koficApiKey = "a00c456adf2ded3c39df93f44cf40503";
        System.out.println(koficApiKey + " koficApiKey");

        String tmdbApiKey = "ba65bca7bd473078269aa9976068045a";
        System.out.println(tmdbApiKey + " tmdbApiKey");

        String itemPerPage = "10";
//        String movieSearchJSON = "boxoffice/searchWeeklyBoxOfficeList.json";
//        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/"
//            + movieSearchJSON + "?key=" + koficApiKey + "&targetDt=" + targetDt;
        String apiURL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json" +
            "?key=a00c456adf2ded3c39df93f44cf40503&targetDt="+ targetDt;
        System.out.println(apiURL + " apiURL");
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);
        System.out.println(responseBody + " <<<<<<<<<<<responseBody");

        // JSON -> DB
        movieService.insertWeeklyResponseBody(responseBody);

        System.out.println(responseBody + " responseBody processed");

        Map<String, String> movie = new HashMap<>();
        movie.put("movie", responseBody);
        return movie;
    }

    // 평일(월~목)  데이터베이스에 저장
    @GetMapping("/WeekDayMovieList")
    public Map<String, String> weekdaymovieList() throws JsonProcessingException {
        // 전날 날짜 계산
        LocalDate yesterday = LocalDate.now().minusWeeks(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = yesterday.format(formatter); // targetDt는 YYYYMMDD 형식

        String koficApiKey = "a00c456adf2ded3c39df93f44cf40503";
        System.out.println(koficApiKey + " koficApiKey");

        String tmdbApiKey = "ba65bca7bd473078269aa9976068045a";
        System.out.println(tmdbApiKey + " tmdbApiKey");

        String itemPerPage = "10";
//        String movieSearchJSON = "boxoffice/searchWeeklyBoxOfficeList.json";
//        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/"
//            + movieSearchJSON + "?key=" + koficApiKey + "&targetDt=" + targetDt;
        String apiURL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json" +
            "?key=a00c456adf2ded3c39df93f44cf40503&targetDt="+ targetDt+ "&weekGb=0";
        System.out.println(apiURL + " apiURL");
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);
        System.out.println(responseBody + " <<<<<<<<<<<responseBody");

        // JSON -> DB
        movieService.insertWeekDayResponseBody(responseBody);

        System.out.println(responseBody + " responseBody processed");

        Map<String, String> movie = new HashMap<>();
        movie.put("movie", responseBody);
        return movie;
    }




    //상세 정보 저장
    @GetMapping("/movieListId")
    public Map<String, String> movieListId(String movieCode) throws JsonProcessingException {
        String mCode = null;
        try {
            mCode = URLEncoder.encode(movieCode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }
        // 영화
        String key = "a00c456adf2ded3c39df93f44cf40503"; //
        String movieSerchJSON = "movie/searchMovieInfo.json";
        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/"
            + movieSerchJSON +"?key="+ key +"&movieCd=" + mCode;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);

        System.out.println(responseBody+"  responseBody1"); //1
        // JSON ->  DB
        movieService.movieInfoResultResonBody(responseBody);
        Map<String, String> movie = new HashMap<>();
        System.out.println("상세정보 정보 저장 컨트롤러!!!!!");
        movie.put("movie", responseBody);

        return movie;
    }


}

