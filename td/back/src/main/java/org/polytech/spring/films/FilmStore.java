package org.polytech.spring.films;

import java.util.List;

public interface FilmStore {
    Film create(Film f);

    List<Film> getFilms();

    List<Film> searchFilms(String realisateur, Genre genre);

    Film getFilmbyId(long id);

    Film modifyFilm(long id, Film f);

    void deleteFilm(long id);
}
