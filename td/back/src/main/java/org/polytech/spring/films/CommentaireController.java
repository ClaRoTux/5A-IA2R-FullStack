package org.polytech.spring.films;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {
    private final FilmService filmService;

    public CommentaireController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PutMapping("/{id}")
    public FilmCommentaire modifyComment(@PathVariable("id") Long id, @RequestBody FilmCommentaire c) {
        return filmService.modifyComment(id, c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        filmService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
