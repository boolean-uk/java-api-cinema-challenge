package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column
    private String created_at;

    @Column
    private String updated_at;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties({"movie","id"})
    private List<Screening> screenings;

    public Movie(int id) {
        this.id = id;
    }
}
