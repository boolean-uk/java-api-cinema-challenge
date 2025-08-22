package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Ticket;
import com.booleanuk.api.cinema.models.User;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.UserListResponse;
import com.booleanuk.api.cinema.payload.response.UserResponse;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("customers")
public class UserController {
    @Autowired
    UserRepository repo;

    @Autowired
    TicketRepository ticketRepo;

    @GetMapping
    public ResponseEntity<UserListResponse> getAll(){
        UserListResponse resp = new UserListResponse();
        resp.set(this.repo.findAll());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> putOne(@PathVariable Integer id, @Valid @RequestBody User user){
       User toBeEdited = this.repo.findById(id).orElse(null);
       if(toBeEdited == null){
           ErrorResponse error = new ErrorResponse();
           error.set("User not found");
           return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
       }

       toBeEdited.setUsername(user.getUsername());
       toBeEdited.setEmail(user.getEmail());
       toBeEdited.setPhone(user.getPhone());
       toBeEdited.setUpdatedAt(OffsetDateTime.now());

       this.repo.save(toBeEdited);

       UserResponse resp = new UserResponse();
       resp.set(toBeEdited);
       return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteOne(@PathVariable Integer id){
        User toBeDeleted  = this.repo.findById(id).orElse(null);

        if (toBeDeleted == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.ticketRepo.deleteAll(toBeDeleted.getTickets());

        this.repo.delete(toBeDeleted);
        UserResponse resp = new UserResponse();
        resp.set(toBeDeleted);
        return ResponseEntity.ok(resp);
    }
}
