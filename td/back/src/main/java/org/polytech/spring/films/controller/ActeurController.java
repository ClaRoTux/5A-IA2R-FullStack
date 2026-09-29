package org.polytech.spring.films.controller;

import java.net.URI;
import java.util.List;

import org.polytech.spring.films.dto.ActeurCreationDto;
import org.polytech.spring.films.dto.ActeurDetailDto;
import org.polytech.spring.films.dto.ActeurDto;
import org.polytech.spring.films.dto.FilmDto;
import org.polytech.spring.films.service.ActeurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/acteurs")
public class ActeurController {
    private final ActeurService acteurService;

    public ActeurController(ActeurService acteurService) {
        this.acteurService = acteurService;
    }

    @GetMapping
    public List<ActeurDto> getActeurs() {
        return acteurService.getActeurs();
    }

    @GetMapping("/{id}")
    public ActeurDetailDto getActeurById(@PathVariable("id") Long id) {
        return acteurService.getActeurById(id);
    }

    @PostMapping
    public ResponseEntity<ActeurDto> addActeur(@RequestBody ActeurCreationDto a) {
        ActeurDto saved = acteurService.create(a);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @PutMapping("/{id}")
    public ActeurDto modifyActeur(@PathVariable("id") Long id, @RequestBody ActeurCreationDto a) {
        return acteurService.modifyActeur(id, a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActeur(@PathVariable("id") Long id) {
        acteurService.deleteActeur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/films")
    public List<FilmDto> getFilmsOfActeur(@PathVariable("id") Long id) {
        return acteurService.getFilmsOfActeur(id);
    }
}
