package org.polytech.spring.films.repository;

import java.util.List;

import org.polytech.spring.films.model.FilmCommentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<FilmCommentaire, Long> {

        List<FilmCommentaire> findByFilmIdOrderById(Long filmId);
}
