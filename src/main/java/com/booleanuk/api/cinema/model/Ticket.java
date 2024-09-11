package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString


@Entity
@Table(name="tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;



//	@ManyToOne
//	@JoinColumn(name="movieId") // navn i kolonnen
//	@JsonIgnoreProperties(value = {"screenings"}) // ignorer denne verdien for å unngå recur
//	private Movie movie;

	@ManyToOne
	@JoinColumn(name="customerId") // kolonnen
	@JsonIgnoreProperties(value={"tickets"})
	private Customer customer;


	@ManyToOne
	@JoinColumn(name="screeningId")
	@JsonIgnoreProperties(value={"tickets"})
	private Screening screening;

	@Column
	private int numSeats;

	@Column
	private OffsetDateTime createdAt;

	@Column
	private OffsetDateTime updatedAt;

	public void createdNow(){
		OffsetDateTime n = OffsetDateTime.now();
		this.createdAt = n;
		this.updatedAt = n;
	}

	public void updatedNow(){
		this.updatedAt = OffsetDateTime.now();
	}

}
