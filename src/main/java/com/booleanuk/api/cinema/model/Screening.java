package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.formatter.CustomDateSerializer;
import com.booleanuk.api.cinema.formatter.InstantDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    //@JsonSerialize(using = CustomDateSerializer.class)
    //@JsonSerialize(using = CustomDateSerializer.class)
    //@JsonDeserialize(using = InstantDeserializer.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    //@JsonSerialize(using = CustomDateSerializer.class)
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = InstantDeserializer.class)
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
