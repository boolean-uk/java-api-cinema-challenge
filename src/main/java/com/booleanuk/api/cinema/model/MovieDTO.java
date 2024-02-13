package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieDTO {

	private int id;
	private String title;
	private String rating;
	private String description;
	private int runtimeMins;
	@JsonIgnore
	private List<ScreeningDTO> screenings;
	private String createdAt;
	private String updatedAt;

	public MovieDTO(int id, String title, String rating, String description, int runtimeMins, List<ScreeningDTO> screenings,String createdAt, String updatedAt) {
		this.id = id;
		this.title = title;
		this.rating = rating;
		this.description = description;
		this.runtimeMins = runtimeMins;
		this.screenings=screenings;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}

