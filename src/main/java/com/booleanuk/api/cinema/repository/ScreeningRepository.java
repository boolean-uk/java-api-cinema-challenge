package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findByMovie(Movie movie);
}
