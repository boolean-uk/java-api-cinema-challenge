package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.dto.MovieDto;
import com.booleanuk.api.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<MovieDto> findAllProjectedBy();
}
