package org.spring.codingStory.movieApi.movie.repository;

import org.spring.codingStory.movieApi.movie.entity.MovieDetailEntity;
import org.spring.codingStory.movieApi.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByMovieCd(String movieCd);
    Optional<MovieDetailEntity> findByMovieNm(String movieNm);

    @Query("SELECT m.movieNm FROM movie m")
    List<String> getAllMovieNm();

    // 순위가 낮은 순으로 상위 10개의 영화 엔티티 가져오기
    @Query("SELECT m FROM movie m ORDER BY m.movieRank ASC")
    List<MovieEntity> findTop10ByOrderByMovieRankAsc();
}