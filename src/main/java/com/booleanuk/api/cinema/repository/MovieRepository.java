package com.booleanuk.api.cinema.repository;

import com.booleanuk.api.cinema.model.Movie;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}