package org.spring.codingStory.movieApi.movie.service;


import org.spring.codingStory.movieApi.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MovieApiService {

    @Value("${tmdb.api.key}")
    private String tmdbApiKey; // TMDB API 키

    @Value("${kofic.api.key}")
    private String koficApiKey; // 영화진흥원 API 키

    private final RestTemplate restTemplate;

    @Autowired
    public MovieApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MovieEntity getMovieById(Long movieId) {
        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + tmdbApiKey;
        ResponseEntity<MovieEntity> responseEntity = restTemplate.getForEntity(apiUrl, MovieEntity.class);
        return responseEntity.getBody();

    }

}