package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.models.Movie;

import java.time.LocalDateTime;

public class MovieDto {
    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.rating = movie.getRating();
        this.description = movie.getDescription();
        this.runtimeMins = movie.getRuntimeMins();
        this.createdAt = movie.getCreatedTime();
        this.updatedAt = movie.getUpdatedTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
