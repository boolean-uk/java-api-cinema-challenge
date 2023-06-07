package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie extends CinemaEntity{
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "rating", nullable = false)
    private String rating;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "runtime_mins", nullable = false)
    private int runtimeMins;

}
