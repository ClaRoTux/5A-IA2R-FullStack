package org.polytech.spring.films;

import java.time.LocalDate;
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

    public void validateComment(FilmCommentaire comment) {
        List<String> errors = new ArrayList<>();
        if (comment.getAuteur() == null || comment.getAuteur().isBlank())
            errors.add("auteur");
        if (comment.getMessage() == null || comment.getMessage().isBlank())
            errors.add("message");
        if (!errors.isEmpty())
            throw new InvalidCommentException("Missing fields: " + String.join(", ", errors));
    }

    public List<FilmCommentaire> getFilmCommentsbyID(Long id) {
        return store.getFilmCommentsbyID(id);
    }

    public FilmCommentaire addComments(long id, FilmCommentaire comment) {
        validateComment(comment);
        comment.setDate(LocalDate.now());
        return store.addComments(id, comment);
    }

    public FilmCommentaire modifyComment(Long id, FilmCommentaire c) {
        if (c.getMessage() == null || c.getMessage().isBlank())
            throw new InvalidCommentException("Missing fields: message");
        return store.modifyComment(id, c);
    }

    public void deleteComment(Long id) {
        store.deleteComment(id);
    }

}
