package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
