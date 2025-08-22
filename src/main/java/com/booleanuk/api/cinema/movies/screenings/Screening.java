package com.booleanuk.api.cinema.movies.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.generic.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
  private int id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JsonIgnoreProperties(value = {"screenings"})
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @Column(nullable = false)
  private int screenNumber;

  @Column(nullable = false)
  private int capacity;

  // TODO: use datetime type
  @Column(nullable = false)
  private String startsAt;

  @Column(nullable = false)
  private String createdAt = LocalDateTime.now().toString();

  @Column(nullable = false)
  private String updatedAt = this.createdAt;

  @Override
  public void update(Screening source) {
    this.screenNumber = source.screenNumber;
    this.capacity = source.capacity;
    this.startsAt = source.startsAt;
    this.createdAt = source.createdAt;
    this.updatedAt = source.updatedAt;
  }
}