package org.spring.codingStory.movieApi.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieInfoResponse {
    private List<MovieInfoResult> movieInfoResult;
}
