package com.booleanuk.api.cinema.core.repository;

import com.booleanuk.api.cinema.core.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long>{
}
