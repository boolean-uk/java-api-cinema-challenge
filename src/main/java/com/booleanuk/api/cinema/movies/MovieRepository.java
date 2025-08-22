package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.movies.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
}
