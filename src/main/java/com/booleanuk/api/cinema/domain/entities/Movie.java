package com.booleanuk.api.cinema.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity{
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String rating;

    @Column(nullable = false)
    private String description;

    @Column(name = "runtime_mins", nullable = false)
    private Integer runtimeMins;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screening> screenings;


    public Movie(String title, String rating, String description, Integer runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.screenings = new ArrayList<>();
    }
}