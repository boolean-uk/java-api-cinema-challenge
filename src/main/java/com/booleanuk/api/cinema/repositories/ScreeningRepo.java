package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepo extends JpaRepository<Screening,Integer> {
}
