package com.booleanuk.api.cinema.mapper;

import com.booleanuk.api.cinema.movie.model.Movie;
import com.booleanuk.api.cinema.movie.model.MovieRequestDTO;
import com.booleanuk.api.cinema.movie.model.MovieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    // Ignored the targets when mapping.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Movie toEntity(MovieRequestDTO requestDTO);

    MovieResponseDTO toResponseDTO(Movie movie);
}
