package com.booleanuk.api.cinema.screening.repository;

import com.booleanuk.api.cinema.movie.model.Movie;
import com.booleanuk.api.cinema.screening.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    public List<Screening> findScreeningsByMovie(Movie movie);
}
