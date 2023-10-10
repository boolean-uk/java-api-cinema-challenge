package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runtimeMins")
    private int runtimeMins;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"movie"})
    private List<Screening> screenings;

    public Movie(int id, String title, String rating, String description, int runtimeMins, Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Movie() {
    }
}
