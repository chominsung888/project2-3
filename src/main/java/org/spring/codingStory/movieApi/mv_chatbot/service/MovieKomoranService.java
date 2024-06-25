package org.spring.codingStory.movieApi.mv_chatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.spring.codingStory.movieApi.movie.dto.MovieInfoResultReson;
import org.spring.codingStory.movieApi.movie.entity.MovieDetailEntity;
import org.spring.codingStory.movieApi.movie.entity.MovieEntity;
import org.spring.codingStory.movieApi.movie.entity.WeekDayMovieEntity;
import org.spring.codingStory.movieApi.movie.entity.WeeklyMovieEntity;
import org.spring.codingStory.movieApi.movie.repository.MovieDetailRepository;
import org.spring.codingStory.movieApi.movie.repository.MovieRepository;
import org.spring.codingStory.movieApi.movie.repository.WeekDayMovieRepository;
import org.spring.codingStory.movieApi.movie.repository.WeeklyMovieRepository;
import org.spring.codingStory.movieApi.mv_chatbot.dto.MovieAnswerDTO;
import org.spring.codingStory.movieApi.mv_chatbot.dto.MovieInfo;
import org.spring.codingStory.movieApi.mv_chatbot.dto.MovieMessageDTO;
import org.spring.codingStory.movieApi.mv_chatbot.entity.MovieChatBotIntention;
import org.spring.codingStory.movieApi.mv_chatbot.repository.MovieChatBotIntentionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class MovieKomoranService {

    private final Komoran komoran;
    private final MovieRepository movieRepository;
    private final MovieDetailRepository movieDetailRepository;
    private final MovieChatBotIntentionRepository movieChatBotIntentionRepository;
    private final WeekDayMovieRepository weekDayMovieRepository;
    private final WeeklyMovieRepository weeklyMovieRepository;

    public MovieMessageDTO nlpAnalyze(String message) {
        KomoranResult result = komoran.analyze(message);

        // 문자에서 명사들만 추출한 목록 중복 제거해서 set
        Set<String> nouns = result.getNouns().stream()
                .collect(Collectors.toSet());

        return analyzeToken(nouns);
    }

    private MovieMessageDTO analyzeToken(Set<String> nouns) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MovieMessageDTO movieMessageDTO = MovieMessageDTO.builder()
                .time(today.format(timeFormatter))
                .build();

        for (String token : nouns) {
            if (token.contains("오늘")) {
                return processBoxOfficeRanking();
            } else if (token.contains("안녕")) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy(년) MM(월) dd(일)");
                movieMessageDTO.today(today.format(dateFormatter));
                MovieAnswerDTO answer = MovieAnswerDTO.builder()
                        .text("안녕하세요! 오늘은 " + today.format(dateFormatter) + "입니다."
                                + "<br>" + "<button onclick=\"findDetail()\">오늘의 박스오피스</button>"
                                + "<br>" + "<button onclick=\"findDetail2()\">주중의 박스오피스</button>"
                                + "<br>" + "<button onclick=\"findDetail1()\">주말의 박스오피스</button>")
                        .build();
                movieMessageDTO.answer(answer);
                return movieMessageDTO;
            }else if (token.contains("주중")) {
                return processWeekDayBoxOfficeRanking();
            }else if (token.contains("주말")) {
                return processWeeklyDayBoxOfficeRanking();
            } else {
                // 입력된 영화 제목이 데이터베이스에 있는지 확인하여 정보 제공
                List<MovieInfoResultReson.MovieInfoResult.MovieInfo> movieInfos = getMovieInfo(token);
                if (!movieInfos.isEmpty()) {
                    MovieAnswerDTO answer = MovieAnswerDTO.builder()
                            .text("영화 " + token + "에 대한 정보입니다.")
                            .movieDetail(movieInfos.get(0))  // 첫 번째 영화 정보 객체 사용
                            .build();
                    movieMessageDTO.answer(answer);
                    return movieMessageDTO;
                }
            }
        }

//         입력된 메시지에 따른 기본 응답 처리
        MovieAnswerDTO answer = MovieAnswerDTO.builder()
                .text("죄송합니다, 해당 정보를 찾을 수 없습니다.")
                .build();
        movieMessageDTO.answer(answer);

        return movieMessageDTO;
    }

    public List<MovieInfoResultReson.MovieInfoResult.MovieInfo> getMovieInfo(String movieName) {
        Optional<MovieDetailEntity> movieEntities = movieDetailRepository.findByMovieNm(movieName);
        return movieEntities.stream()
                .map(this::convertToMovieInfo)
                .collect(Collectors.toList());
    }

    private MovieInfoResultReson.MovieInfoResult.MovieInfo convertToMovieInfo(MovieDetailEntity movieEntity) {
        return MovieInfoResultReson.MovieInfoResult.MovieInfo.builder()
                .movieCd(movieEntity.getMovieCd())
                .movieNm(movieEntity.getMovieNm())
                .showTm(movieEntity.getShowTm())
                .openDt(movieEntity.getOpenDt())
                .prdtStatNm(movieEntity.getPrdtStatNm())
                .typeNm(movieEntity.getTypeNm())
                .nations(convertNations(movieEntity.getPeopleNm()))  // 예시에서는 배우이름을 국적으로 변환하는 메소드로 사용
                .genres(convertGenres(movieEntity.getGenreNm()))
                .directors(convertDirectors(movieEntity.getPeopleDirectorNm()))
                .actors(convertActors(movieEntity.getPeopleNm()))
                .showTypes(convertShowTypes(movieEntity.getShowTypeNm()))
                .companys(convertCompanys(movieEntity.getCompanyNm()))
                .audits(convertAudits())  // 예시에서는 빈 리스트 반환
                .staffs(convertStaffs())  // 예시에서는 빈 리스트 반환
                .build();
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Nation> convertNations(String peopleNm) {
        // 예시에서는 배우 이름을 국적으로 변환하는 메소드로 가정
        return Collections.singletonList(
                MovieInfoResultReson.MovieInfoResult.MovieInfo.Nation.builder()
                        .nationNm("국적 정보")  // 실제로는 peopleNm을 통해 국적 정보를 가져와야 함
                        .build()
        );
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Genre> convertGenres(String genreNames) {
        // genreNames는 콤마(,)로 구분된 장르 이름 문자열이라 가정
        List<String> genreList = Arrays.asList(genreNames.split(","));
        return genreList.stream()
                .map(genreName -> MovieInfoResultReson.MovieInfoResult.MovieInfo.Genre.builder()
                        .genreNm(genreName)
                        .build())
                .collect(Collectors.toList());
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Director> convertDirectors(String peopleDirectorNm) {
        // peopleDirectorNm은 감독 이름 문자열이라 가정
        return Collections.singletonList(
                MovieInfoResultReson.MovieInfoResult.MovieInfo.Director.builder()
                        .peopleNm(peopleDirectorNm)
                        .peopleNmEn("")  // 감독의 영문 이름, 실제로는 데이터에서 가져와야 함
                        .build()
        );
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Actor> convertActors(String peopleNm) {
        // peopleNm은 배우 이름 문자열이라 가정
        return Collections.singletonList(
                MovieInfoResultReson.MovieInfoResult.MovieInfo.Actor.builder()
                        .peopleNm(peopleNm)
                        .peopleNmEn("")  // 배우의 영문 이름, 실제로는 데이터에서 가져와야 함
                        .cast("")  // 배역 정보, 예시에서는 임의로 설정
                        .castEn("")  // 배역의 영문 정보, 예시에서는 임의로 설정
                        .build()
        );
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.ShowType> convertShowTypes(String showTypeNm) {
        // showTypeNm은 상영 형태 이름 문자열이라 가정
        return Collections.singletonList(
                MovieInfoResultReson.MovieInfoResult.MovieInfo.ShowType.builder()
                        .showTypeGroupNm("상영 형태 그룹")
                        .showTypeNm(showTypeNm)
                        .build()
        );
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Company> convertCompanys(String companyNm) {
        // companyNm은 제작사 이름 문자열이라 가정
        return Collections.singletonList(
                MovieInfoResultReson.MovieInfoResult.MovieInfo.Company.builder()
                        .companyCd("Company Code")  // 제작사 코드, 예시에서는 임의로 설정
                        .companyNm(companyNm)
                        .companyNmEn("Company's English Name")  // 제작사의 영문 이름, 예시에서는 임의로 설정
                        .companyPartNm("제작 부서 이름")  // 제작사 부서 이름, 예시에서는 임의로 설정
                        .build()
        );
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Audit> convertAudits() {
        // 검사 정보 리스트, 예시에서는 빈 리스트 반환
        return Collections.emptyList();
    }

    private List<MovieInfoResultReson.MovieInfoResult.MovieInfo.Staff> convertStaffs() {
        // 스태프 정보 리스트, 예시에서는 빈 리스트 반환
        return Collections.emptyList();
    }

    private MovieMessageDTO processBoxOfficeRanking() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MovieMessageDTO movieMessageDTO = MovieMessageDTO.builder()
                .time(today.format(timeFormatter))
                .build();

        // 영화 서비스를 통해 박스 오피스 순위를 가져옵니다.
        // 여기에서는 상위 10개 영화만 가져오도록 설정
        List<MovieEntity> top10BoxOfficeMovies = movieRepository.findTop10ByOrderByMovieRankAsc();

        // 영화 정보를 MovieInfo 리스트로 변환
        List<MovieInfo> movieInfos = top10BoxOfficeMovies.stream()
                .map(movie -> MovieInfo.builder()
                        .movieNm(movie.getMovieNm())
                        .movieCd(movie.getMovieCd())
                        .openDt(movie.getOpenDt())
                        .audiAcc(movie.getAudiAcc())
                        .build())
                .collect(Collectors.toList());

        MovieAnswerDTO answer = MovieAnswerDTO.builder()
                .keyword("오늘")  // keyword 필드를 '순위'로 설정
                .text("현재 박스 오피스 Top 10 순위입니다.")
                .infoList(movieInfos)
                .build();

        movieMessageDTO.answer(answer);
        return movieMessageDTO;
    }

    private MovieMessageDTO processWeekDayBoxOfficeRanking() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MovieMessageDTO movieMessageDTO = MovieMessageDTO.builder()
                .time(today.format(timeFormatter))
                .build();

        // 영화 서비스를 통해 박스 오피스 순위를 가져옵니다.
        // 여기에서는 상위 10개 영화만 가져오도록 설정
        List<WeekDayMovieEntity> top10WeekDayBoxOfficeMovies = weekDayMovieRepository.findTop10ByOrderByMovieRankAsc();

        // 영화 정보를 MovieInfo 리스트로 변환
        List<MovieInfo> movieInfos = top10WeekDayBoxOfficeMovies.stream()
                .map(movie -> MovieInfo.builder()
                        .movieNm(movie.getMovieNm())
                        .movieCd(movie.getMovieCd())
                        .openDt(movie.getOpenDt())
                        .audiAcc(movie.getAudiAcc())
                        .build())
                .collect(Collectors.toList());

        MovieAnswerDTO answer = MovieAnswerDTO.builder()
                .keyword("주중")  // keyword 필드를 '순위'로 설정
                .text("주중 박스 오피스 Top 10 순위입니다.")
                .infoList(movieInfos)
                .build();

        movieMessageDTO.answer(answer);
        return movieMessageDTO;
    }

    private MovieMessageDTO processWeeklyDayBoxOfficeRanking() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MovieMessageDTO movieMessageDTO = MovieMessageDTO.builder()
                .time(today.format(timeFormatter))
                .build();

        // 영화 서비스를 통해 박스 오피스 순위를 가져옵니다.
        // 여기에서는 상위 10개 영화만 가져오도록 설정
        List<WeeklyMovieEntity> top10WeeklyBoxOfficeMovies = weeklyMovieRepository.findTop10ByOrderByMovieRankAsc();

        // 영화 정보를 MovieInfo 리스트로 변환
        List<MovieInfo> movieInfos = top10WeeklyBoxOfficeMovies.stream()
                .map(movie -> MovieInfo.builder()
                        .movieNm(movie.getMovieNm())
                        .movieCd(movie.getMovieCd())
                        .openDt(movie.getOpenDt())
                        .audiAcc(movie.getAudiAcc())
                        .build())
                .collect(Collectors.toList());

        MovieAnswerDTO answer = MovieAnswerDTO.builder()
                .keyword("주말")  // keyword 필드를 '순위'로 설정
                .text("주말 박스 오피스 Top 10 순위입니다.")
                .infoList(movieInfos)
                .build();

        movieMessageDTO.answer(answer);
        return movieMessageDTO;
    }

    private MovieInfoResultReson.MovieInfoResult.MovieInfo analyzeToken(Set<String> next, Optional<MovieChatBotIntention> upper) {
        for (String token : next) {
            Optional<MovieChatBotIntention> result = decisionTree(token, upper.orElseThrow(() -> new NoSuchElementException("상위 의도가 존재하지 않습니다")));
            if (result.isPresent()) {
                Object answerObject = result.get().getAnswer();
                if (answerObject instanceof MovieInfoResultReson.MovieInfoResult.MovieInfo) {
                    return (MovieInfoResultReson.MovieInfoResult.MovieInfo) answerObject;
                } else if (answerObject instanceof ArrayList) {
                    // Handle ArrayList case, assuming it contains MovieInfo objects
                    List<MovieInfoResultReson.MovieInfoResult.MovieInfo> movieInfos = (List<MovieInfoResultReson.MovieInfoResult.MovieInfo>) answerObject;
                    if (!movieInfos.isEmpty()) {
                        return movieInfos.get(0); // Assuming you want the first MovieInfo object
                    }
                }
            }
        }
        throw new NoSuchElementException("상위 의도에 해당하는 응답을 찾을 수 없습니다.");
    }

    private Optional<MovieChatBotIntention> decisionTree(String token, MovieChatBotIntention upper) {
        return movieChatBotIntentionRepository.findByNameAndUpper(token, upper);
    }
}
