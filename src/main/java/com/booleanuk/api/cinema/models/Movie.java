package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;
    @Column
    private String rating;
    @Column
    private String description;
    @Column
    private Integer duration;

    @OneToMany(mappedBy = "movie_id")
    @JsonIgnoreProperties("movie_id")
    private List<Screening> screenings;

    @Override
    public boolean haveNullFields() {
        return title == null || rating == null || description == null || duration == null;
    }

    @Override
    public void copyOverData(final Model model) {
        try {
            Movie _other = (Movie) model;

            title = _other.title;
            rating = _other.rating;
            description = _other.description;
            duration = _other.duration;
            screenings = _other.screenings;
        }
        catch (Exception e) {}
    }
}
