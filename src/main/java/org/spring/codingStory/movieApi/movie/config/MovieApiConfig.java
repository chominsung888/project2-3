package org.spring.codingStory.movieApi.movie.config;


import org.spring.codingStory.movieApi.movie.dto.MovieInfoResultReson;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "tmdb")
public class MovieApiConfig {


    private String apiKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


    @Bean
    public MovieInfoResultReson.MovieInfoResult.MovieInfo.Company company() {
        return new MovieInfoResultReson.MovieInfoResult.MovieInfo.Company();
    }
    @Bean
    public MovieInfoResultReson.MovieInfoResult.MovieInfo.Director director() {
        return new MovieInfoResultReson.MovieInfoResult.MovieInfo.Director();
    }
    @Bean
    public MovieInfoResultReson.MovieInfoResult.MovieInfo.Genre genre() {
        return new MovieInfoResultReson.MovieInfoResult.MovieInfo.Genre();
    }



}
