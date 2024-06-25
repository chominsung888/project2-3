package org.spring.codingStory.movieApi.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieInfoResult {
        private MovieInfo movieInfo;
        private String source;
}
