package org.polytech.spring.films.controller;

import org.polytech.spring.films.dto.CommentaireCreationDto;
import org.polytech.spring.films.dto.CommentaireDto;
import org.polytech.spring.films.service.CommentaireService;
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
    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @PutMapping("/{id}")
    public CommentaireDto modifyComment(@PathVariable("id") Long id, @RequestBody CommentaireCreationDto c) {
        return commentaireService.modifyComment(id, c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id) {
        commentaireService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
