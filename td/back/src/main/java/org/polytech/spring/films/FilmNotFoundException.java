package org.polytech.spring.films;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(long id) {
        super("No film with the ID " + id);
    }
}
