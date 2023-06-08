package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.service.MovieService;
import com.booleanuk.api.cinema.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningService screeningService;
    @GetMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public List<Screening> getAll(@PathVariable(name ="id") long id){
        return screeningService.getScreenings(id);
    }
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Screening get(@PathVariable(name="id") long id){
//        return screeningService.getScreening(id);
//    }
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Screening update(@PathVariable(name="id") long id,@RequestBody Screening screening){
//        return screeningService.updateScreening(id,screening);
//    }
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Screening create(@PathVariable(name = "id") long id,@RequestBody Screening screening){
        return screeningService.createScreening(id,screening);
    }
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Screening delete(@PathVariable(name="id") long id){
//        return screeningService.deleteScreening(id);
//    }
}
