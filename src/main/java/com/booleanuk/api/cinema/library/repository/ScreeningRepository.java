package com.booleanuk.api.cinema.library.repository;

import com.booleanuk.api.cinema.library.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<Screening> findByMovieId(int movieId);
}
