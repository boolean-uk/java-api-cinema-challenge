package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
@Table(name = "screening")
public class Screening extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer screenNumber;
    @Column
    private Integer capacity;
    @Column
    private String startsAt;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "title", "rating", "description", "duration" })
    private Movie movie_id;

    @OneToMany(mappedBy = "screening_id")
    @JsonIgnoreProperties("screening_id")
    private List<Ticket> tickets;

    @Override
    public boolean haveNullFields() {
        return screenNumber == null || capacity == null || startsAt == null;
    }

    @Override
    public void copyOverData(Model model) {
        try {
            Screening _other = (Screening) model;

            screenNumber = _other.screenNumber;
            capacity = _other.capacity;
            startsAt = _other.startsAt;

            movie_id = _other.movie_id;
            tickets = _other.tickets;
        }
        catch (Exception e) {}
    }
}
