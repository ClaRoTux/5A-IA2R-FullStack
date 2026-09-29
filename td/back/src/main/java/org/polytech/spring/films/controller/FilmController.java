package org.polytech.spring.films.controller;

import java.net.URI;
import java.util.List;

import org.polytech.spring.films.dto.ActeurDto;
import org.polytech.spring.films.dto.CommentaireCreationDto;
import org.polytech.spring.films.dto.CommentaireDto;
import org.polytech.spring.films.dto.FilmCreationDto;
import org.polytech.spring.films.dto.FilmDetailDto;
import org.polytech.spring.films.dto.FilmDto;
import org.polytech.spring.films.dto.PageDto;
import org.polytech.spring.films.dto.RoleCreationDto;
import org.polytech.spring.films.model.Genre;
import org.polytech.spring.films.service.CommentaireService;
import org.polytech.spring.films.service.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final CommentaireService commentaireService;

    public FilmController(FilmService filmService, CommentaireService commentaireService) {
        this.filmService = filmService;
        this.commentaireService = commentaireService;
    }

    @GetMapping
    public PageDto<FilmDto> getFilms(@RequestParam(name = "realisateur", required = false) String realisateur,
            @RequestParam(name = "genre", required = false) Genre genre,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort) {
        return filmService.getFilms(realisateur, genre, page, size, sort);
    }

    @GetMapping("/{id}")
    public FilmDetailDto getFilmbyId(@PathVariable("id") Long id) {
        return filmService.getFilmbyId(id);
    }

    @PostMapping
    public ResponseEntity<FilmDto> addFilm(@RequestBody FilmCreationDto f) {
        FilmDto saved = filmService.create(f);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public FilmDto modifyFilm(@PathVariable("id") Long id, @RequestBody FilmCreationDto f) {
        return filmService.modifyFilm(id, f);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable("id") Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/acteurs")
    public List<ActeurDto> getActeursOfFilm(@PathVariable("id") Long id) {
        return filmService.getActeursOfFilm(id);
    }

    @PostMapping("/{id}/acteurs/{acteurId}")
    public FilmDetailDto addActeur(@PathVariable("id") Long id, @PathVariable("acteurId") Long acteurId,
            @RequestBody(required = false) RoleCreationDto role) {
        return filmService.addActeur(id, acteurId, role);
    }

    @DeleteMapping("/{id}/acteurs/{acteurId}")
    public ResponseEntity<Void> removeActeur(@PathVariable("id") Long id, @PathVariable("acteurId") Long acteurId) {
        filmService.removeActeur(id, acteurId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/commentaires")
    public List<CommentaireDto> getFilmCommentsbyID(@PathVariable("id") Long id) {
        return commentaireService.getFilmCommentsbyID(id);
    }

    @PostMapping("/{id}/commentaires")
    public ResponseEntity<CommentaireDto> addComments(@PathVariable("id") Long id,
            @RequestBody CommentaireCreationDto comment) {
        CommentaireDto saved = commentaireService.addComments(id, comment);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/commentaires/{id}")
                .buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }
}
