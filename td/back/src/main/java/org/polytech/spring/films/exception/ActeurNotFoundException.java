package org.polytech.spring.films.exception;

public class ActeurNotFoundException extends RuntimeException {
    public ActeurNotFoundException(long id) {
        super("No actor with the ID " + id);
    }
}
