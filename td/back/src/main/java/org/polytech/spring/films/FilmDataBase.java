package org.polytech.spring.films;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class FilmDataBase implements FilmStore {

    private List<Film> films = new ArrayList<>();
    private long lastId = 0;
    private long lastCommentId = 0;

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
    public List<Film> searchFilms(String realisateur, Genre genre) {
        return films.stream()
                .filter(f -> realisateur == null || realisateur.isBlank()
                        || f.getRealisateur().toLowerCase().contains(realisateur.toLowerCase()))
                .filter(f -> genre == null || f.getGenre() == genre)
                .toList();
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

    @Override
    public List<FilmCommentaire> getFilmCommentsbyID(Long id) {
        return List.copyOf(getFilmbyId(id).getCommentaires());
    }

    @Override
    public FilmCommentaire addComments(long id, FilmCommentaire comment) {
        Film f = getFilmbyId(id);
        comment.setId(++lastCommentId);
        f.getCommentaires().add(comment);
        return comment;
    }

    @Override
    public FilmCommentaire modifyComment(Long id, FilmCommentaire c) {
        FilmCommentaire comment = getCommentbyId(id);
        comment.setMessage(c.getMessage());
        return comment;
    }

    private FilmCommentaire getCommentbyId(long id) {
        for (Film f : films) {
            for (FilmCommentaire c : f.getCommentaires()) {
                if (c.getId() == id) {
                    return c;
                }
            }
        }
        throw new CommentaireNotFoundException(id);
    }

    @Override
    public void deleteComment(Long id) {
        for (Film f : films) {
            for (FilmCommentaire c : f.getCommentaires()) {
                if (c.getId() == id) {
                    f.getCommentaires().remove(c);
                    return;
                }
            }
        }
        throw new CommentaireNotFoundException(id);
    }
}
