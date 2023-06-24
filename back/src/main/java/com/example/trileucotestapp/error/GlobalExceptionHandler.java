package com.example.trileucotestapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handlePersonNotFound(
            PersonNotFoundException ex) {
        CustomErrorResponse errors = new CustomErrorResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlanetNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handlePlanetNotFound(
            PlanetNotFoundException ex) {
        CustomErrorResponse errors = new CustomErrorResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
