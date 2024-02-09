package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    @Query("SELECT s FROM Screening s WHERE s.movie.id = ?1")
    List<Screening> findAllByMovieId(int id);
}
