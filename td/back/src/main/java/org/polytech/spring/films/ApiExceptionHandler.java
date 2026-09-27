package org.polytech.spring.films;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public ProblemDetail handleNotFound(FilmNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Film not found");
        return pd;
    }

    @ExceptionHandler(CommentaireNotFoundException.class)
    public ProblemDetail handleCommentNotFound(CommentaireNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Comment not found");
        return pd;
    }

    @ExceptionHandler({ InvalidFilmException.class, InvalidCommentException.class })
    public ProblemDetail handleInvalid(RuntimeException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Invalid request");
        return pd;
    }

}
