package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Teapot;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.TeapotResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teapot")
public class TeapotController {

    /**
     * Brews tea if tea is given
     * @param teapot - Teapot which has been given liquid contents
     * @return Response Entity showing results
     */
    @PostMapping()
    public ResponseEntity<Response<?>> brewTea(@RequestBody Teapot teapot)  {
        if(teapot.getContents().equalsIgnoreCase("Tea"))
        {
            TeapotResponse teapotResponse = new TeapotResponse();
            teapotResponse.set("Successfully brewed tea", teapot);
            return ResponseEntity.ok(teapotResponse);
        }
        else if(teapot.getContents().equalsIgnoreCase("Coffee"))
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Can't brew coffee, I am a teapot");
            return new ResponseEntity<>(errorResponse, HttpStatus.I_AM_A_TEAPOT);
        }
        else
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("I don't know what this is, but I don't want it. I want tea");
            return new ResponseEntity<>(errorResponse, HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
