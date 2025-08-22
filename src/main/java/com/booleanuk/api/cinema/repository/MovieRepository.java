package com.booleanuk.api.cinema.repository;

import com.booleanuk.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
