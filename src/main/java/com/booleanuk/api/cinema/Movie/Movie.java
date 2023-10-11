package com.booleanuk.api.cinema.Movie;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runtime_mins")
    private int runtimeMins;

    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
    }
}
