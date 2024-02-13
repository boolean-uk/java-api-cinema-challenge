package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JsonIgnoreProperties(value = "tickets")
	@JoinColumn(name = "screeningId")
	@JsonIgnore
	private Screening screening;

	@ManyToOne
	@JsonIgnoreProperties(value = "tickets")
	@JoinColumn(name = "customerId", referencedColumnName = "id")
	@JsonIgnore
	private Customer customer;

	@Column
	private int numSeats;
	@Column(nullable = false)
	private OffsetDateTime createdAt;
	@Column(nullable = false)
	private OffsetDateTime updatedAt;

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

	public Ticket(int id) {
		this.id = id;
	}

	public Ticket(Screening screening, Customer customer, int numSeats) {
		this.screening = screening;
		this.customer = customer;
		this.numSeats = numSeats;
	}
}
