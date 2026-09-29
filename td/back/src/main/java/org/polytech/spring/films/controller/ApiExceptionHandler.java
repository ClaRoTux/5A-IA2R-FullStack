package org.polytech.spring.films.controller;

import org.polytech.spring.films.exception.ActeurNotFoundException;
import org.polytech.spring.films.exception.CommentaireNotFoundException;
import org.polytech.spring.films.exception.FilmNotFoundException;
import org.polytech.spring.films.exception.InvalidActeurException;
import org.polytech.spring.films.exception.InvalidCommentException;
import org.polytech.spring.films.exception.InvalidFilmException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public ProblemDetail handleNotFound(FilmNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Film not found");
        return pd;
    }

    @ExceptionHandler(ActeurNotFoundException.class)
    public ProblemDetail handleActeurNotFound(ActeurNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Actor not found");
        return pd;
    }

    @ExceptionHandler(CommentaireNotFoundException.class)
    public ProblemDetail handleCommentNotFound(CommentaireNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Comment not found");
        return pd;
    }

    @ExceptionHandler({ InvalidFilmException.class, InvalidActeurException.class, InvalidCommentException.class })
    public ProblemDetail handleInvalid(RuntimeException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Invalid request");
        return pd;
    }

}
