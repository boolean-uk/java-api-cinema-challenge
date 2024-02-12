package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    //
    //  Movie methods
    //

    /**
     * Get all movies in the database
     *
     * @return List of Movie objects
     */
    @GetMapping
    public ResponseEntity<Object> getAllMovies()   {
        return ResponseHandler.generateResponse(
                "Successfully returned a list of all movies",
                HttpStatus.OK,
                this.movieRepository.findAll()
                );
    }

    /**
     * Add a new movie to the database
     * Request Body:
     *  title: String REQUIRED
     *  rating: String REQUIRED
     *  description: String REQUIRED
     *  runtimeMins: int REQUIRED
     *  screenings: List<Screening> optional
     *
     * @param movie - Movie to add to database
     * @return Response code signifying success/failure, and movie which was added to database
     * @see Movie::verifyMovie()
     */
    @PostMapping
    public ResponseEntity<Object> createMovie(@RequestBody Movie movie)  {
        if(!movie.verifyMovie())
            return ResponseHandler.generateException(
                    "Error",
                    "Could not create a new movie, please check all fields are correct",
                    HttpStatus.BAD_REQUEST);
        this.movieRepository.save(movie);

        return ResponseHandler.generateResponse(
                "Successfully created a new movie",
                HttpStatus.CREATED,
                movie);
    }

    /**
     * Updates an existing movie in the database (if found)
     * Request Body:
     *  title: String optional
     *  rating: String optional
     *  description: String optional
     *  runtimeMins: int optional
     *
     * @param id - Unique identifier of movie to edit (int)
     * @param movie - Updated movie
     * @return Response code signifying success/failure, and movie which was updated
     * @see Movie::verifyMovie()
     */
    @PutMapping("{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable int id, @RequestBody Movie movie)    {
        Movie movieToUpdate = this.movieRepository.findById(id)
                .orElse(null);
        if (movieToUpdate == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No movie with that id found",
                    HttpStatus.NOT_FOUND);

        // Updates the values of the movie
        if(movie.getTitle()  != null)   movieToUpdate.setTitle(movie.getTitle());
        if(movie.getRating() != null)   movieToUpdate.setRating(movie.getRating());
        if(movie.getDescription() != null) movieToUpdate.setDescription(movie.getDescription());
        if(movie.getRuntimeMins() != null) movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return ResponseHandler.generateResponse(
                "Successfully updated the specified movie",
                HttpStatus.CREATED,
                movieToUpdate
        );
    }

    /**
     * Delete a specified movie from the database
     *
     * @param id - ID of movie to delete
     * @return Response code signifying success/failure, and movie which was deleted
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable int id)  {
        // Check movie id
        Movie movieToDelete = this.movieRepository.findById(id)
                .orElse(null);
        if (movieToDelete == null)
            return ResponseHandler.generateException(
                    "Error",
                    "No movie with that id found",
                    HttpStatus.NOT_FOUND);
        // Delete all the screenings for the movie first due to constraint
        for(Screening screening : movieToDelete.getScreenings())
            this.screeningRepository.delete(screening);
        // Then delete the movie
        this.movieRepository.delete(movieToDelete);

        return ResponseHandler.generateResponse(
                "Successfully deleted the specified movie",
                HttpStatus.CREATED,
                movieToDelete
        );
    }

}
