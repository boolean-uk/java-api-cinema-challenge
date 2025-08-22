package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.screenings.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "movies")
@JsonIgnoreProperties({"screenings"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @Column
    private int runtimeMins;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie", orphanRemoval = true)
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();

    }

    public Movie(int id) {
        this.id = id;
    }

    public Movie() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }
}
