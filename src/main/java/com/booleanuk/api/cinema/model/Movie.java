package com.booleanuk.api.cinema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Movie {
    @Id
    private long id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Movie(String title, String rating, String description, int runtimeMins, LocalDateTime createdAt) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(int runtimeMins) {
        this.runtimeMins = runtimeMins;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && runtimeMins == movie.runtimeMins && Objects.equals(title, movie.title) && Objects.equals(rating, movie.rating) && Objects.equals(description, movie.description) && Objects.equals(createdAt, movie.createdAt) && Objects.equals(updatedAt, movie.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rating, description, runtimeMins, createdAt, updatedAt);
    }
}
