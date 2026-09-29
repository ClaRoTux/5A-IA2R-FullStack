package org.polytech.spring.films.service;

import java.util.ArrayList;
import java.util.List;

import org.polytech.spring.films.dto.ActeurCreationDto;
import org.polytech.spring.films.dto.ActeurDetailDto;
import org.polytech.spring.films.dto.ActeurDto;
import org.polytech.spring.films.dto.ActeurMapper;
import org.polytech.spring.films.dto.FilmDto;
import org.polytech.spring.films.dto.FilmMapper;
import org.polytech.spring.films.exception.ActeurNotFoundException;
import org.polytech.spring.films.exception.InvalidActeurException;
import org.polytech.spring.films.model.Acteur;
import org.polytech.spring.films.repository.ActeurRepository;
import org.polytech.spring.films.repository.FilmRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActeurService {
    private final ActeurRepository acteurRepository;
    private final FilmRepository filmRepository;

    public ActeurService(ActeurRepository acteurRepository, FilmRepository filmRepository) {
        this.acteurRepository = acteurRepository;
        this.filmRepository = filmRepository;
    }

    @Transactional(readOnly = true)
    public List<ActeurDto> getActeurs() {
        return acteurRepository.findAll(Sort.by("id")).stream().map(ActeurMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ActeurDetailDto getActeurById(long id) {
        return acteurRepository.findByIdWithFilms(id)
                .map(ActeurMapper::toDetailDto)
                .orElseThrow(() -> new ActeurNotFoundException(id));
    }

    @Transactional
    public ActeurDto create(ActeurCreationDto dto) {
        validateActeur(dto);
        return ActeurMapper.toDto(acteurRepository.save(ActeurMapper.toEntity(dto)));
    }

    @Transactional
    public ActeurDto modifyActeur(long id, ActeurCreationDto dto) {
        validateActeur(dto);
        Acteur acteur = findActeur(id);
        ActeurMapper.updateEntity(acteur, dto);
        return ActeurMapper.toDto(acteur);
    }

    @Transactional
    public void deleteActeur(long id) {
        acteurRepository.delete(findActeur(id));
    }

    @Transactional(readOnly = true)
    public List<FilmDto> getFilmsOfActeur(long id) {
        if (!acteurRepository.existsById(id)) {
            throw new ActeurNotFoundException(id);
        }
        return filmRepository.findByRolesActeurIdOrderByTitre(id).stream().map(FilmMapper::toDto).toList();
    }

    public void validateActeur(ActeurCreationDto dto) {
        List<String> errors = new ArrayList<>();
        if (dto.firstname() == null || dto.firstname().isBlank())
            errors.add("firstname");
        if (dto.name() == null || dto.name().isBlank())
            errors.add("name");
        if (!errors.isEmpty())
            throw new InvalidActeurException("Missing fields: " + String.join(", ", errors));
    }

    private Acteur findActeur(long id) {
        return acteurRepository.findById(id).orElseThrow(() -> new ActeurNotFoundException(id));
    }
}
