package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movie, Integer> {

}
