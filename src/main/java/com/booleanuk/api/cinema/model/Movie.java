package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "rating")
    private String rating;
    @Column(name = "description")
    private String description;
    @Column(name = "runtime_minutes")
    private int runtimeMinutes;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMinutes) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMinutes = runtimeMinutes;
    }

    public Movie(int id) {
        this.id = id;
    }
}
