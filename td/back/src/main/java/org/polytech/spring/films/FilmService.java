package org.polytech.spring.films;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class FilmService {
    private final FilmStore store;
    private final Map<String, Comparator<Film>> tris = Map.of(
            "id", Comparator.comparing(Film::getId),
            "titre", Comparator.comparing(Film::getTitre),
            "realisateur", Comparator.comparing(Film::getRealisateur),
            "dateSortie", Comparator.comparing(Film::getDateSortie),
            "genre", Comparator.comparing(Film::getGenre));

    public FilmService(FilmStore filmStore) {
        this.store = filmStore;
    }

    public Film create(Film f) {
        validateFilm(f);
        return store.create(f);
    }

    public List<Film> getFilms(String realisateur, Genre genre, long page, long size, String sort) {
        Comparator<Film> comparator = tris.get(sort);
        if (page < 0 || size < 1 || size > 100 || comparator == null) {
            throw new InvalidFilmException("Invalid pagination parameters");
        }
        return store.searchFilms(realisateur, genre).stream().sorted(comparator).skip(page * size).limit(size).toList();
    }

    public Film getFilmbyId(long id) {
        return store.getFilmbyId(id);
    }

    public Film modifyFilm(long id, Film f) {
        validateFilm(f);
        return store.modifyFilm(id, f);
    }

    public void deleteFilm(long id) {
        store.deleteFilm(id);
    }

    public void validateFilm(Film f) {
        List<String> errors = new ArrayList<>();
        if (f.getTitre() == null || f.getTitre().isBlank())
            errors.add("title");
        if (f.getRealisateur() == null || f.getRealisateur().isBlank())
            errors.add("director");
        if (f.getDateSortie() == null)
            errors.add("release date");
        if (f.getGenre() == null)
            errors.add("genre");
        if (!errors.isEmpty()) {
            throw new InvalidFilmException("Missing fields: " + String.join(", ", errors));
        }
    }

}
