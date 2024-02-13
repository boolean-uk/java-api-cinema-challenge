package com.booleanuk.api.cinema.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TicketDTO {

	private int id;
	private int numSeats;
	private String createdAt;
	private String updatedAt;

	public TicketDTO(int id, int numSeats, String createdAt, String updatedAt) {
		this.id = id;
		this.numSeats = numSeats;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
