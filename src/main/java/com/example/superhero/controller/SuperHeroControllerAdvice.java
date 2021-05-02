package com.example.superhero.controller;

import com.example.superhero.dto.ErrorDTO;
import com.example.superhero.exception.SuperHeroNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SuperHeroControllerAdvice {

    @ExceptionHandler(value = {SuperHeroNotFoundException.class})
    protected ResponseEntity<ErrorDTO> handleNotFound(final RuntimeException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(createResponse(httpStatus, ex), httpStatus);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<ErrorDTO> genericHandle(final RuntimeException ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(createResponse(httpStatus, ex), httpStatus);
    }

    private ErrorDTO createResponse(final HttpStatus status, final Exception ex) {
        return ErrorDTO.builder().code(status.value()).message(ex.getMessage()).build();
    }
}
