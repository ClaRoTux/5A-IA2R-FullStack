package org.polytech.spring.films;

public class CommentaireNotFoundException extends RuntimeException {
    public CommentaireNotFoundException(long id) {
        super("No comment with the ID " + id);
    }
}
