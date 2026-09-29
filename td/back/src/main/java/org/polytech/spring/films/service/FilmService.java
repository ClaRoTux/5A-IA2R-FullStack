package org.polytech.spring.films.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.polytech.spring.films.dto.ActeurDto;
import org.polytech.spring.films.dto.ActeurMapper;
import org.polytech.spring.films.dto.FilmCreationDto;
import org.polytech.spring.films.dto.FilmDetailDto;
import org.polytech.spring.films.dto.FilmDto;
import org.polytech.spring.films.dto.FilmMapper;
import org.polytech.spring.films.dto.PageDto;
import org.polytech.spring.films.dto.RoleCreationDto;
import org.polytech.spring.films.exception.ActeurNotFoundException;
import org.polytech.spring.films.exception.FilmNotFoundException;
import org.polytech.spring.films.exception.InvalidFilmException;
import org.polytech.spring.films.model.Acteur;
import org.polytech.spring.films.model.Film;
import org.polytech.spring.films.model.Genre;
import org.polytech.spring.films.repository.ActeurRepository;
import org.polytech.spring.films.repository.FilmRepository;
import org.polytech.spring.films.repository.RoleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilmService {
    private static final Set<String> tris = Set.of("id", "titre", "realisateur", "dateSortie", "genre");

    private final FilmRepository filmRepository;
    private final ActeurRepository acteurRepository;
    private final RoleRepository roleRepository;

    public FilmService(FilmRepository filmRepository, ActeurRepository acteurRepository,
            RoleRepository roleRepository) {
        this.filmRepository = filmRepository;
        this.acteurRepository = acteurRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public FilmDto create(FilmCreationDto f) {
        validateFilm(f);
        Film saved = filmRepository.save(FilmMapper.toEntity(f));
        return FilmMapper.toDto(saved);
    }

    private Sort parseSort(String sort) {
        String[] parts = sort.split(",");
        String champ = parts[0].trim();
        if (!tris.contains(champ) || parts.length > 2) {
            throw new InvalidFilmException("Invalid sort field: " + sort);
        }
        Sort.Direction dir = Sort.Direction.ASC;
        if (parts.length == 2) {
            dir = Sort.Direction.fromOptionalString(parts[1].trim())
                    .orElseThrow(() -> new InvalidFilmException("Invalid sort direction: " + parts[1]));
        }
        return Sort.by(dir, champ);
    }

    @Transactional(readOnly = true)
    public PageDto<FilmDto> getFilms(String realisateur, Genre genre, long page, long size, String sort) {
        if (page < 0 || size < 1 || size > 100) {
            throw new InvalidFilmException("Invalid pagination parameters");
        }
        Pageable pageable = PageRequest.of((int) page, (int) size, parseSort(sort));
        String motif = (realisateur == null || realisateur.isBlank())
                ? "%"
                : "%" + realisateur.toLowerCase() + "%";
        return PageDto.of(filmRepository.search(motif, genre, pageable).map(FilmMapper::toDto));
    }

    @Transactional(readOnly = true)
    public FilmDetailDto getFilmbyId(long id) {
        return filmRepository.findByIdWithActeurs(id)
                .map(FilmMapper::toDetailDto)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    @Transactional
    public FilmDto modifyFilm(long id, FilmCreationDto f) {
        validateFilm(f);
        Film film = findFilm(id);
        FilmMapper.updateEntity(film, f);
        return FilmMapper.toDto(film);
    }

    @Transactional
    public void deleteFilm(long id) {
        filmRepository.delete(findFilm(id));
    }

    @Transactional(readOnly = true)
    public List<ActeurDto> getActeursOfFilm(long id) {
        if (!filmRepository.existsById(id)) {
            throw new FilmNotFoundException(id);
        }
        return acteurRepository.findActeursOfFilm(id).stream().map(ActeurMapper::toDto).toList();
    }

    @Transactional
    public FilmDetailDto addActeur(long filmId, long acteurId, RoleCreationDto role) {
        Film film = filmRepository.findByIdWithActeurs(filmId)
                .orElseThrow(() -> new FilmNotFoundException(filmId));
        Acteur acteur = acteurRepository.findById(acteurId)
                .orElseThrow(() -> new ActeurNotFoundException(acteurId));
        String personnage = role == null ? null : role.personnage();
        film.getRoles().stream()
                .filter(r -> r.getActeur().getId().equals(acteurId))
                .findFirst()
                .ifPresentOrElse(r -> {
                    if (personnage != null) {
                        r.setPersonnage(personnage);
                    }
                }, () -> film.addRole(acteur, personnage));
        return FilmMapper.toDetailDto(film);
    }

    @Transactional
    public void removeActeur(long filmId, long acteurId) {
        Film film = findFilm(filmId);
        if (!acteurRepository.existsById(acteurId)) {
            throw new ActeurNotFoundException(acteurId);
        }
        roleRepository.findByFilmIdAndActeurId(filmId, acteurId).ifPresent(film::removeRole);
    }

    public void validateFilm(FilmCreationDto f) {
        List<String> errors = new ArrayList<>();
        if (f.titre() == null || f.titre().isBlank())
            errors.add("title");
        if (f.realisateur() == null || f.realisateur().isBlank())
            errors.add("director");
        if (f.dateSortie() == null)
            errors.add("release date");
        if (f.genre() == null)
            errors.add("genre");
        if (!errors.isEmpty()) {
            throw new InvalidFilmException("Missing fields: " + String.join(", ", errors));
        }
    }

    private Film findFilm(long id) {
        return filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
    }
}
