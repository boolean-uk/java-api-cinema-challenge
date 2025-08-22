package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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



    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties(value ={"screenings", "tickets", "movie","customer","screening"})
    private List<Screening> screenings;

    @Column
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt=LocalDateTime.now();


}
