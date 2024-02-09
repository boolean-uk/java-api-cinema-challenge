package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "runtime_mins")
    private int runtime_mins;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("screening")
    private List<Screening> screenings;

    public Movie(String title, String description, int runtime_mins) {
        this.title = title;
        this.description = description;
        this.runtime_mins = runtime_mins;
    }

    public Movie(String title, String description, int runtime_mins, Screening screening) {
        this.title = title;
        this.description = description;
        this.runtime_mins = runtime_mins;
        this.screenings.add(screening);
    }

    public Movie(int id) {
        this.id = id;
    }
}
