package com.booleanuk.api.cinema.movies.screenings;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "screenings")
public class Screening implements Serializable, GenericEntity<Screening> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(nullable = false)
  int screenNumber;

  @Column(nullable = false)
  int capacity;

  // TODO: use datetime type
  @Column(nullable = false)
  String startsAt;

  @Column(nullable = false)
  String createdAt;

  @Column(nullable = false)
  String updatedAt;

  public Screening(int screenNumber, int capacity, String startsAt) {
    this.screenNumber = screenNumber;
    this.capacity = capacity;
    this.startsAt = startsAt;
    this.createdAt = LocalDateTime.now().toString();
    this.updatedAt = this.createdAt;
  }

  @Override
  public void update(Screening source) {
    this.id = source.id;
    this.screenNumber = source.screenNumber;
    this.capacity = source.capacity;
    this.startsAt = source.startsAt;
    this.createdAt = source.createdAt;
    this.updatedAt = source.updatedAt;
  }
}