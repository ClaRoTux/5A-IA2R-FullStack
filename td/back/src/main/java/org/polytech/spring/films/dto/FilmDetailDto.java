package org.polytech.spring.films.dto;

import java.time.LocalDate;
import java.util.List;

import org.polytech.spring.films.model.Genre;

public record FilmDetailDto(
                Long id,
                String titre,
                String realisateur,
                LocalDate dateSortie,
                Genre genre,
                List<RoleDto> acteurs) {
}
