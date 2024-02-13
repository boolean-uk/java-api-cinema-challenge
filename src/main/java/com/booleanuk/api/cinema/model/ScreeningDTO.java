package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScreeningDTO {

	private int id;
	@JsonIgnore
	private Movie movie;

	private int screenNumber;

	private String startsAt;

	private int capacity;

	private String createdAt;

	private String updatedAt;
	@JsonIgnore
	private List<Ticket> tickets;

	public ScreeningDTO(int id, Movie movie, int screenNumber, String startsAt, int capacity, String createdAt, String updatedAt, List<Ticket> tickets) {
		this.id = id;
		this.movie = movie;
		this.screenNumber = screenNumber;
		this.startsAt = startsAt;
		this.capacity = capacity;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tickets = tickets;
	}
}
