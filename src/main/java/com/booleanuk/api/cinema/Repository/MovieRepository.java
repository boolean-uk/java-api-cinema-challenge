package com.booleanuk.api.cinema.Repository;

import com.booleanuk.api.cinema.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
