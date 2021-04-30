package com.example.superhero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuperHeroNotFoundException extends RuntimeException {

    private static final String ERROR_MSG = "The SuperHero with id %s was not found";

    public SuperHeroNotFoundException(final Long id) {
        super(String.format(ERROR_MSG, id));
    }
}
