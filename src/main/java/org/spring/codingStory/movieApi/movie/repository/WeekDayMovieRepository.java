package org.spring.codingStory.movieApi.movie.repository;

import org.spring.codingStory.movieApi.movie.entity.WeekDayMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeekDayMovieRepository extends JpaRepository<WeekDayMovieEntity, Long> {

    Optional<WeekDayMovieEntity> findByMovieCd(String movieCd);

    List<WeekDayMovieEntity> findTop10ByOrderByMovieRankAsc();
}