package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.formatter.CustomDateSerializer;
import com.booleanuk.api.cinema.formatter.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "screenings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screening extends CinemaEntity{

    @Column(name = "screen_number", nullable = false)
    private int screenNumber;
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @Column(name = "starts_at")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startsAt;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable=false)
    @JsonIgnore
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket> tickets;
}
