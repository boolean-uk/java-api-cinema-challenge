package com.booleanuk.api.cinema.repository;
import com.booleanuk.api.cinema.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
}
