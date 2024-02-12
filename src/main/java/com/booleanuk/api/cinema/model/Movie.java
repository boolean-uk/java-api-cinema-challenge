package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private int runtimeMins;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("screening")
    private List<Screening> screenings;

    public Movie(String title, String description, int runtime_mins) {
        this.title = title;
        this.description = description;
        this.runtimeMins = runtime_mins;
    }

    public Movie(String title, String description, int runtime_mins, Screening screening) {
            this.title = title;
            this.description = description;
            this.runtimeMins = runtime_mins;
            this.screenings.add(screening);
        }

    public Movie(int id) {
        this.id = id;
    }
}
