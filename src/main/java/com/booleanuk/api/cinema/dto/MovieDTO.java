package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.model.Movie;

import java.time.LocalDateTime;

public class MovieDTO {
    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.rating = movie.getRating();
        this.description = movie.getDescription();
        this.runtimeMin = movie.getRuntimeMin();
        this.createdAt = movie.getCreatedAt();
        this.updatedAt = movie.getUpdatedAt();
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

    public int getRuntimeMin() {
        return runtimeMin;
    }

    public void setRuntimeMin(int runtimeMin) {
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
