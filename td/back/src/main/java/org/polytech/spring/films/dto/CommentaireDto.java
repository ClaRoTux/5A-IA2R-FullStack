package org.polytech.spring.films.dto;

import java.time.LocalDate;

public record CommentaireDto(
        Long id,
        String auteur,
        LocalDate date,
        String message) {

}
