package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
}
