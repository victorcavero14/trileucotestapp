package com.example.trileucotestapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler for handling the PersonNotFoundException.
     *
     * @param ex the PersonNotFoundException to handle.
     * @return a ResponseEntity containing a CustomErrorResponse and an HTTP status code.
     */
    @ExceptionHandler(PersonNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handlePersonNotFound(
            PersonNotFoundException ex) {
        CustomErrorResponse errors = new CustomErrorResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for handling the PlanetNotFoundException.
     *
     * @param ex the PlanetNotFoundException to handle.
     * @return a ResponseEntity containing a CustomErrorResponse and an HTTP status code.
     */
    @ExceptionHandler(PlanetNotFoundException.class)
    protected ResponseEntity<CustomErrorResponse> handlePlanetNotFound(
            PlanetNotFoundException ex) {
        CustomErrorResponse errors = new CustomErrorResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
