package com.booleanuk.api.cinema.Response;

import com.booleanuk.api.cinema.Model.Movie;

public class MovieResponse {

    private String status;

    private Movie data;

    public MovieResponse(String status, Movie data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Movie getData() {
        return data;
    }

    public void setData(Movie data) {
        this.data = data;
    }
}
