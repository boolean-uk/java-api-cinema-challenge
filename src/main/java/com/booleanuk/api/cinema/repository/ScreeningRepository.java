package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Screening;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScreeningRepository extends CrudRepository<Screening, Long> {
    List<Screening> findScreeningByMovie_Id(Long id);
}
