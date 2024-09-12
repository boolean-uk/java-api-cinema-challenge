package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Rating cannot be blank")
    @Column(name = "rating", nullable = false)
    private String rating;

    @NotBlank(message = "Description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Runtime must be specified")
    @Column(name = "runtime_min", nullable = false)
    private int runtimeMin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "movie-screenings")
    private List<Screening> screenings;


    public Movie(String title, String rating, String description, Integer runtimeMin) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMin = runtimeMin;
        this.screenings = new ArrayList<>();
    }

    public Movie() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

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

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }
}