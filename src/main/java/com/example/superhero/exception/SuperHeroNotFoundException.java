package com.example.superhero.exception;


public class SuperHeroNotFoundException extends RuntimeException {

    private static final String ERROR_MSG = "The SuperHero with id %s was not found";

    public SuperHeroNotFoundException(final Long id) {
        super(String.format(ERROR_MSG, id));
    }
}
