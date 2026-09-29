package org.polytech.spring.films.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.polytech.spring.films.dto.CommentaireCreationDto;
import org.polytech.spring.films.dto.CommentaireDto;
import org.polytech.spring.films.dto.CommentaireMapper;
import org.polytech.spring.films.exception.CommentaireNotFoundException;
import org.polytech.spring.films.exception.FilmNotFoundException;
import org.polytech.spring.films.exception.InvalidCommentException;
import org.polytech.spring.films.model.Film;
import org.polytech.spring.films.model.FilmCommentaire;
import org.polytech.spring.films.repository.CommentaireRepository;
import org.polytech.spring.films.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentaireService {
    private final CommentaireRepository commentaireRepository;
    private final FilmRepository filmRepository;

    public CommentaireService(CommentaireRepository commentaireRepository, FilmRepository filmRepository) {
        this.commentaireRepository = commentaireRepository;
        this.filmRepository = filmRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentaireDto> getFilmCommentsbyID(long filmId) {
        if (!filmRepository.existsById(filmId)) {
            throw new FilmNotFoundException(filmId);
        }
        return commentaireRepository.findByFilmIdOrderById(filmId).stream().map(CommentaireMapper::toDto).toList();
    }

    @Transactional
    public CommentaireDto addComments(long filmId, CommentaireCreationDto dto) {
        validateComment(dto);
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(filmId));
        FilmCommentaire comment = CommentaireMapper.toEntity(dto);
        comment.setDate(LocalDate.now());
        comment.setFilm(film);
        return CommentaireMapper.toDto(commentaireRepository.save(comment));
    }

    @Transactional
    public CommentaireDto modifyComment(long id, CommentaireCreationDto dto) {
        if (dto.message() == null || dto.message().isBlank())
            throw new InvalidCommentException("Missing fields: message");
        FilmCommentaire comment = findComment(id);
        comment.setMessage(dto.message());
        return CommentaireMapper.toDto(comment);
    }

    @Transactional
    public void deleteComment(long id) {
        commentaireRepository.delete(findComment(id));
    }

    public void validateComment(CommentaireCreationDto comment) {
        List<String> errors = new ArrayList<>();
        if (comment.auteur() == null || comment.auteur().isBlank())
            errors.add("auteur");
        if (comment.message() == null || comment.message().isBlank())
            errors.add("message");
        if (!errors.isEmpty())
            throw new InvalidCommentException("Missing fields: " + String.join(", ", errors));
    }

    private FilmCommentaire findComment(long id) {
        return commentaireRepository.findById(id).orElseThrow(() -> new CommentaireNotFoundException(id));
    }
}
