package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.dto.ScreeningDto;
import com.booleanuk.api.cinema.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    List<ScreeningDto> findByMovieId(int id);
}
