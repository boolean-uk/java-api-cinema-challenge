package com.booleanuk.api.cinema.screening.repository;

import com.booleanuk.api.cinema.screening.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
}
