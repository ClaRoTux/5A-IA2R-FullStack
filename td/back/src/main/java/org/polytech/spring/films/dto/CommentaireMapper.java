package org.polytech.spring.films.dto;

import org.polytech.spring.films.model.FilmCommentaire;

public final class CommentaireMapper {

    public static CommentaireDto toDto(FilmCommentaire commentaire) {
        return new CommentaireDto(commentaire.getId(), commentaire.getAuteur(), commentaire.getDate(),
                commentaire.getMessage());
    }

    public static FilmCommentaire toEntity(CommentaireCreationDto c) {
        FilmCommentaire comment = new FilmCommentaire();
        comment.setAuteur(c.auteur());
        comment.setMessage(c.message());
        return comment;
    }

    private CommentaireMapper() {
    }
}
