package com.booleanuk.api.cinema.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name="Screenings")
public class Screening {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="movieId") // navn i kolonnen
	@JsonIgnoreProperties(value = {"screenings"}) // ignorer denne verdien for å unngå recur
	private Movie movie;

	@Column
	private int screenNumber;

	@Column
	private OffsetDateTime startsAt;

	@Column
	private int capacity;

	@Column
	private OffsetDateTime createdAt;

	@Column
	private OffsetDateTime updatedAt;



}
