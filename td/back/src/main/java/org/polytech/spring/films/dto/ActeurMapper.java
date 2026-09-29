package org.polytech.spring.films.dto;

import java.util.Comparator;

import org.polytech.spring.films.model.Acteur;
import org.polytech.spring.films.model.Film;
import org.polytech.spring.films.model.Role;

public final class ActeurMapper {
    public static ActeurDto toDto(Acteur acteur) {
        return new ActeurDto(acteur.getId(), acteur.getFirstname(), acteur.getName());
    }

    public static Acteur toEntity(ActeurCreationDto acteur) {
        Acteur a = new Acteur();
        updateEntity(a, acteur);
        return a;
    }

    public static void updateEntity(Acteur a, ActeurCreationDto acteur) {
        a.setFirstname(acteur.firstname());
        a.setName(acteur.name());
    }

    public static ActeurDetailDto toDetailDto(Acteur a) {
        return new ActeurDetailDto(a.getId(), a.getFirstname(), a.getName(),
                a.getRoles().stream()
                        .map(Role::getFilm)
                        .sorted(Comparator.comparing(Film::getTitre))
                        .map(FilmMapper::toDto)
                        .toList());
    }

    private ActeurMapper() {
    }
}
