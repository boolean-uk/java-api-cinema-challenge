package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JsonIgnoreProperties(value = "screenings")
	@JoinColumn(name = "movieId", referencedColumnName = "id")
	@JsonIgnore
	private Movie movie;

	@Column
	private int screenNumber;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
	private OffsetDateTime startsAt;

	@Column
	private int capacity;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
	private OffsetDateTime createdAt;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
	private OffsetDateTime updatedAt;


	@OneToMany(mappedBy = "screening")
	@JsonIgnore
	private List<Ticket> tickets;

	public Screening(Movie movie, int screenNumber, String startsAt, int capacity) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

		this.movie = movie;
		this.screenNumber = screenNumber;
		this.startsAt = OffsetDateTime.parse(startsAt,formatter);
		this.capacity = capacity;
	}

	@PrePersist
	public void prePersist() {
		OffsetDateTime now = OffsetDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = OffsetDateTime.now();
	}

	public Screening(int id) {
		this.id = id;
	}

	public Screening(Movie movie, int screenNumber, OffsetDateTime startsAt, int capacity) {
		this.movie = movie;
		this.screenNumber = screenNumber;
		this.startsAt = startsAt;
		this.capacity = capacity;
	}

}
