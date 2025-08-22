package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {}
