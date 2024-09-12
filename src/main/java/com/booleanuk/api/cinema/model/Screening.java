package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer screenNumber;
    private Integer capacity;
    private String startsAt;
    private String createdAt;
    private String updatedAt;


    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties(value = {"screenings", "movie"})
    @JsonIgnore
    private Movie movie;

    public Screening(Integer screenNumber, Integer capacity, String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    public Screening(int id){
        this.id = id;
    }
}
