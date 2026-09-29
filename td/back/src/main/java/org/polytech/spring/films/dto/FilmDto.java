package org.polytech.spring.films.dto;

import java.time.LocalDate;

import org.polytech.spring.films.model.Genre;

public record FilmDto(
        Long id,
        String titre,
        String realisateur,
        LocalDate dateSortie,
        Genre genre) {

}
