package com.booleanuk.api.cinema.Dtos.movies;

import com.booleanuk.api.cinema.Dtos.screenings.ScreeningNew;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class MovieRequestDto {

    private String title;
    private String rating;
    private String description;
    private int runtimeMins;
    private List<ScreeningNew> screenings;

    public Movie toMovie(){
        return new Movie(0,title,rating,description,runtimeMins,new ArrayList<>(), null,null);
    }
}
