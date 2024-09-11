package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runtimeMin")
    private Integer runtimeMin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference(value = "movie-screenings")
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, Integer runtimeMin) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMin = runtimeMin;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Movie(String title, String rating, String description, Integer runtimeMin, LocalDateTime time) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMin = runtimeMin;
        this.updatedAt = time;
    }

    public Movie() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRuntimeMin() {
        return runtimeMin;
    }

    public void setRuntimeMin(Integer runtimeMin) {
        this.runtimeMin = runtimeMin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
