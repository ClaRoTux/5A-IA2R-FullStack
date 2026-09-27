package org.polytech.spring.films;

import java.net.URI;
import java.util.List;

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

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms(@RequestParam(name = "realisateur", required = false) String realisateur,
            @RequestParam(name = "genre", required = false) Genre genre,
            @RequestParam(name = "page", required = false, defaultValue = "0") Long page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Long size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort) {
        return filmService.getFilms(realisateur, genre, page, size, sort);
    }

    @GetMapping("/{id}")
    public Film getFilmbyId(@PathVariable("id") Long id) {
        return filmService.getFilmbyId(id);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody Film f) {
        Film saved = filmService.create(f);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public Film modifyFilm(@PathVariable("id") Long id, @RequestBody Film f) {
        return filmService.modifyFilm(id, f);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable("id") Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

}
