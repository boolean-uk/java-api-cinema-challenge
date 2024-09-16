package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.booleanuk.api.cinema.ResponseWrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("movies/{movieId}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ResponseWrapper<Object>> create(@PathVariable("movieId") Integer movieId, @RequestBody Screening newScreening) {
        try {
            newScreening.setMovieId(movieId);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            newScreening.setCreatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));
            newScreening.setUpdatedAt(LocalDateTime.parse(currentDateTime.format(formatter)));
            Screening savedScreening = this.screeningRepository.save(newScreening);
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", savedScreening.getId());
            response.put("screenNumber", savedScreening.getScreenNumber());
            response.put("capacity", savedScreening.getCapacity());
            response.put("startsAt", savedScreening.getStartsAt());
            response.put("createdAt", currentDateTime.format(formatter));
            response.put("updatedAt", currentDateTime.format(formatter));

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper<>("success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>("error", "Could not create screening, please check all required fields are correct."));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseWrapper getAll() {
        return new ResponseWrapper<>("success", this.screeningRepository.findAll());
    }



}
