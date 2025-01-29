package com.booleanuk.api.cinema.model;

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
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String rating;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer runtimeMins;

    @Column
    private LocalDate createdAt;

    @Column
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, Integer runtimeMins){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Movie(String title, String rating, String description, Integer runtimeMins, List<Screening> screenings){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
        this.screenings = screenings;
    }

    public Movie(){
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
}
