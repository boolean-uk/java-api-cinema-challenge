package com.booleanuk.api.cinema.extension.repository;

import com.booleanuk.api.cinema.extension.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long>{
}
