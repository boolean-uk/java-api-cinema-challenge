package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends GenericRepository<Movie> {
}