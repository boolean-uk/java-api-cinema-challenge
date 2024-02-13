package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
@JsonIgnoreProperties("screenings")
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
    private int runTimeMins;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private ZonedDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties("movie")
    private List<Screening> screenings;

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public Movie(String title, String rating, String description, int runTimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimeMins = runTimeMins;
    }

    public Movie(int id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return this.updatedAt.withZoneSameInstant(ZoneId.systemDefault());
    }
    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt.withZoneSameInstant(ZoneId.systemDefault());
    }
}
