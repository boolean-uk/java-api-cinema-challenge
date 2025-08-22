package com.booleanuk.api.cinema.repositories;

import com.booleanuk.api.cinema.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
