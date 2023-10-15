package com.booleanuk.api.cinema.Repository;

import com.booleanuk.api.cinema.Model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {

    List<Screening> findByMovieId (int id);
}
