package org.polytech.spring.films.dto;

import java.util.List;

public record ActeurDetailDto(
        Long id,
        String firstname,
        String name,
        List<FilmDto> films) {

}
