package com.booleanuk.api.cinema.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name="Movies")
public class Movie{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private int runtimeMins;

	@Column
	private OffsetDateTime createdAt;

	@Column
	private OffsetDateTime updatedAt;

	@Column
	private String rating;


	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"movie"}) // ignorer movie sine screenings.
	private List<Screening> screenings;


}