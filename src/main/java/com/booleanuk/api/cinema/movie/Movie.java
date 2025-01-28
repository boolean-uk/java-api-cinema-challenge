package com.booleanuk.api.cinema.movie;


import com.booleanuk.api.cinema.screening.Screening;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private String rating;

    @Column(name = "description")
    private String description;

    @Column(name = "runtime_mins")
    private int runtime_mins;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @OneToMany(mappedBy = "movie")
    @JsonIgnoreProperties({"movie", "id"})
    private List<Screening> screenings;

    public Movie(String title, String rating, String description, int runtime_mins){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtime_mins = runtime_mins;
    }

    public Movie(int id){
        this.id = id;
    }

}
