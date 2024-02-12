package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
	private OffsetDateTime startsAt;

	@Column
	private int capacity;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;


	@OneToMany(mappedBy = "screening")
	//@JsonIgnoreProperties(value = "screening")
	@JsonIgnore
	private List<Ticket> tickets;
	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
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
