package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Detailed.class)
    private int id;

    @Column
    @JsonView(Views.BasicInfo.class)
    private String title;

    @Column
    @JsonView(Views.BasicInfo.class)
    private String rating;

    @Column
    @JsonView(Views.BasicInfo.class)
    private String description;

    @Column
    @JsonView(Views.BasicInfo.class)
    @PositiveOrZero
    private int runtimeMins;

    @Column
    @JsonView(Views.Detailed.class)
    private OffsetDateTime createdAt;

    @Column
    @JsonView(Views.Detailed.class)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIncludeProperties({"screenNumber", "capacity", "startsAt"})
    private List<Screening> screenings;

    @PrePersist
    public void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Movie(int id) {
        this.id = id;
    }

    public Movie(String title, String rating, String description) {
        this.title = title;
        this.rating = rating;
        this.description = description;
    }

    public Movie(String title, String rating, String description, int runTime) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runTime;
    }

    public Movie(String title, String rating, String description, int runTime, List<Screening> screenings) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runTime;
        this.screenings = screenings;
    }

//    TODO link to screening
}
