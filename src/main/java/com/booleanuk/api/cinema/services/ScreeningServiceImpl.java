package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.ScreeningResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.entities.Movie;
import com.booleanuk.api.cinema.domain.entities.Screening;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository, ModelMapper modelMapper) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ScreeningResponseDTO createScreening(Long movieId, CreateScreeningRequestDTO screeningDTO) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Screening screening = modelMapper.map(screeningDTO, Screening.class);
            screening.setMovie(movieOptional.get());
            LocalDateTime currentDateTime = LocalDateTime.now();
            screening.setCreatedAt(currentDateTime);
            screening.setUpdatedAt(currentDateTime);

            Screening savedScreening = screeningRepository.save(screening);
            return modelMapper.map(savedScreening, ScreeningResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE, movieId));
        }
    }

    @Override
    public List<ScreeningResponseDTO> getScreeningsByMovieId(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            List<Screening> screenings = screeningRepository.findByMovie(movieOptional.get());
            return screenings.stream()
                    .map(screening -> modelMapper.map(screening, ScreeningResponseDTO.class))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE, movieId));
        }
    }

    @Override
    public ScreeningResponseDTO getScreeningById(Long screeningId) {
        Optional<Screening> screeningOptional = screeningRepository.findById(screeningId);
        if (screeningOptional.isPresent()) {
            return modelMapper.map(screeningOptional.get(), ScreeningResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.SCREENING_NOT_FOUND_MESSAGE, screeningId));
        }
    }

    @Override
    public ScreeningResponseDTO updateScreening(Long screeningId, UpdateScreeningRequestDTO screeningDTO) {
        Optional<Screening> screeningOptional = screeningRepository.findById(screeningId);
        if (screeningOptional.isPresent()) {
            Screening existingScreening = screeningOptional.get();

            if (hasUpdates(existingScreening, screeningDTO)) {
                existingScreening.setUpdatedAt(LocalDateTime.now());
            }
            modelMapper.map(screeningDTO, existingScreening);
            Screening updatedScreening = screeningRepository.save(existingScreening);
            return modelMapper.map(updatedScreening, ScreeningResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.SCREENING_NOT_FOUND_MESSAGE, screeningId));
        }
    }

    @Override
    public ScreeningResponseDTO deleteScreening(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.SCREENING_NOT_FOUND_MESSAGE, screeningId)));

        screeningRepository.delete(screening);
        return modelMapper.map(screening, ScreeningResponseDTO.class);
    }

    private boolean hasUpdates(Screening existingScreening, UpdateScreeningRequestDTO screeningDTO) {
        boolean hasUpdates = !Objects.equals(existingScreening.getScreenNumber(), screeningDTO.getScreenNumber()) ||
                !Objects.equals(existingScreening.getCapacity(), screeningDTO.getCapacity()) ||
                !existingScreening.getStartsAt().equals(screeningDTO.getStartsAt());

        return hasUpdates;
    }
}