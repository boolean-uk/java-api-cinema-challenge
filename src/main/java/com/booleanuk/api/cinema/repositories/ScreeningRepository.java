package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.domain.entities.Movie;
import com.booleanuk.api.cinema.domain.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findByMovie(Movie movie);

}