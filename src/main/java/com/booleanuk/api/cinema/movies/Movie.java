package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.movies.screenings.Screening;
import com.booleanuk.api.generic.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

  @Column(nullable = false)
  private String createdAt = LocalDateTime.now().toString();

  // We could json-ignore this, since it's not specified in the spec, but I prefer having it in.
  @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties(value = "movieId")
  List<Screening> screenings = new ArrayList<>();

  @Column(nullable = false)
  private String updatedAt = this.createdAt;

  @Override
  public void update(Movie source) {
    this.title = source.title;
    this.rating = source.rating;
    this.description = source.description;
    this.runtimeMins = source.runtimeMins;
  }
}