package org.spring.codingStory.movieApi.mv_chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.movieApi.movie.dto.MovieInfoResultReson;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieAnswerDTO {

	private long id;
	private String content;
	private String keyword; // name
	private MovieInfo movie;
	private List<MovieInfo> infoList;
	private String text;
	private MovieInfoResultReson.MovieInfoResult.MovieInfo movieDetail;

	public MovieAnswerDTO movie(MovieInfo movie) {
		this.movie = movie;
		return this;
	}

	public MovieAnswerDTO movie(MovieInfoResultReson.MovieInfoResult.MovieInfo movieDetail) {
		this.movieDetail = movieDetail;
		return this;
	}


	public MovieAnswerDTO text(String text) {
		this.text = text;
		return this;
	}
}
