package org.polytech.spring.films.exception;

public class InvalidActeurException extends RuntimeException {
    public InvalidActeurException(String message) {
        super(message);
    }
}
