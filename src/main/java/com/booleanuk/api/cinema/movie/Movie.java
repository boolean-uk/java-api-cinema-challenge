package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
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
    private String createdAt;

    @Column
    private String updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;

    public Movie(int id){
        setId(id);
    }

    public Movie(String title, String rating, String description, int runtimeMins){
        setTitle(title);
        setRating(rating);
        setDescription(description);
        setRuntimeMins(runtimeMins);
    }

    public Movie(String title, String rating, String description, int runtimeMins, ArrayList<Screening> screenings){
        this(title, rating, description, runtimeMins);
        this.screenings = screenings;
    }
}
