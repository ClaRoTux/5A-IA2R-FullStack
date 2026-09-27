package org.polytech.spring.films;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FilmService {
    private final FilmStore store;

    public FilmService(FilmStore filmStore) {
        this.store = filmStore;
    }

    public Film create(Film f) {
        validateFilm(f);
        return store.create(f);
    }

    public List<Film> getFilms() {
        return store.getFilms();
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
