package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @Column
    private int runtimeMins;

    @CreationTimestamp
    @Column
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties({"movie"})
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
    }
}
