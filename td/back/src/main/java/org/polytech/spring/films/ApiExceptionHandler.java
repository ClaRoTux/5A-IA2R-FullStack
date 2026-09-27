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

    @ExceptionHandler(InvalidFilmException.class)
    public ProblemDetail handleInvalid(InvalidFilmException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Invalid film");
        return pd;
    }

}
