package org.spring.codingStory.movieApi.movie.repository;


import org.spring.codingStory.movieApi.movie.entity.MovieDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieDetailRepository extends JpaRepository<MovieDetailEntity,Long> {
    Optional<MovieDetailEntity> findByMovieCd(String movieCd);

    Optional<MovieDetailEntity> findByMovieNm(String movieNm);
}
