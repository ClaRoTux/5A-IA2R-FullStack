package org.polytech.spring.films;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class FilmDataBase implements FilmStore {

    private List<Film> films = new ArrayList<>();
    private long lastId = 0;

    @Override
    public Film create(Film f) {
        f.setId(++lastId);
        return saveFilm(f);
    }

    public Film saveFilm(Film f) {
        films.add(f);
        return f;
    }

    @Override
    public List<Film> getFilms() {
        return List.copyOf(films);
    }

    @Override
    public Film getFilmbyId(long id) {
        for (int i = 0; i < films.size(); i++) {
            if (films.get(i).getId() == id) {
                return films.get(i);
            }
        }
        throw new FilmNotFoundException(id);
    }

    @Override
    public Film modifyFilm(long id, Film f) {
        Film film = getFilmbyId(id);
        film.setTitre(f.getTitre());
        film.setRealisateur(f.getRealisateur());
        film.setDateSortie(f.getDateSortie());
        film.setGenre(f.getGenre());
        return film;
    }

    @Override
    public void deleteFilm(long id) {
        films.remove(getFilmbyId(id));
    }
}
