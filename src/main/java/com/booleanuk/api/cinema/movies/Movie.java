package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie implements Serializable, GenericEntity<Movie> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String rating;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private int runtimeMins;

  @Override
  public void update(Movie source) {
    this.id = source.id;
    this.title = source.title;
    this.rating = source.rating;
    this.description = source.description;
    this.runtimeMins = source.runtimeMins;
  }
}