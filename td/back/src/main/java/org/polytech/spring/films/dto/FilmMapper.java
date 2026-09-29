package org.polytech.spring.films.dto;

import java.util.Comparator;

import org.polytech.spring.films.model.Film;
import org.polytech.spring.films.model.Role;

public final class FilmMapper {

    public static FilmDto toDto(Film f) {
        return new FilmDto(f.getId(), f.getTitre(), f.getRealisateur(), f.getDateSortie(), f.getGenre());
    }

    public static Film toEntity(FilmCreationDto f_dto) {
        Film f = new Film();
        updateEntity(f, f_dto);
        return f;
    }

    public static void updateEntity(Film f, FilmCreationDto f_dto) {
        f.setTitre(f_dto.titre());
        f.setRealisateur(f_dto.realisateur());
        f.setDateSortie(f_dto.dateSortie());
        f.setGenre(f_dto.genre());
    }

    public static FilmDetailDto toDetailDto(Film f) {
        return new FilmDetailDto(f.getId(), f.getTitre(), f.getRealisateur(), f.getDateSortie(), f.getGenre(),
                f.getRoles().stream()
                        .sorted(Comparator.comparing((Role r) -> r.getActeur().getId()))
                        .map(FilmMapper::toRoleDto)
                        .toList());
    }

    private static RoleDto toRoleDto(Role r) {
        return new RoleDto(r.getActeur().getId(), r.getActeur().getFirstname(), r.getActeur().getName(),
                r.getPersonnage());
    }

    private FilmMapper() {
    }
}
