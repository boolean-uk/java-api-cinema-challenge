package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    @Autowired
    private ScreeningRepository screenRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllScreens() {
        List<Screening> screens = screenRepository.findAll();
        return ResponseEntity.ok(new Response<>("Success", screens));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreen(@RequestBody Screening screen) {
        Screening savedScreen = screenRepository.save(screen);
        return new ResponseEntity<>(new Response<>("Screen created successfully", savedScreen), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateScreen(@PathVariable Long id, @RequestBody Screening screen) {
        Screening existingScreen = screenRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingScreen == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Screen not found", null));
        } else {
            screen.setId(Math.toIntExact(id));
            Screening updatedScreen = screenRepository.save(screen);
            return ResponseEntity.ok(new Response<>("Screen updated successfully", updatedScreen));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteScreen(@PathVariable Long id) {
        Screening existingScreen = screenRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingScreen == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Screen not found", null));
        } else {
            screenRepository.delete(existingScreen);
            return ResponseEntity.ok(new Response<>("Screen deleted successfully", existingScreen));
        }
    }
}
