package com.booleanuk.api.repository;
import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
