package com.latelier.tenisuapi.domain.exception;

public class NoPlayerFoundException extends RuntimeException {

    public NoPlayerFoundException() {
        super("No player found");
    }

    public NoPlayerFoundException(Integer id) {
        super("No player found with id : " + id);
    }
}
