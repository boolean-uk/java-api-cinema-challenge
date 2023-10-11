package com.booleanuk.api.cinema.utils;

import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorUtil {
   public static ResponseEntity<Response<?>> getErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
