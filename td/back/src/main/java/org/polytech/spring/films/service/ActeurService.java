package org.polytech.spring.films.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.polytech.spring.films.dto.ActeurCreationDto;
import org.polytech.spring.films.dto.ActeurDetailDto;
import org.polytech.spring.films.dto.ActeurDto;
import org.polytech.spring.films.dto.ActeurMapper;
import org.polytech.spring.films.dto.FilmDto;
import org.polytech.spring.films.dto.FilmMapper;
import org.polytech.spring.films.dto.PageDto;
import org.polytech.spring.films.exception.ActeurNotFoundException;
import org.polytech.spring.films.exception.InvalidActeurException;
import org.polytech.spring.films.model.Acteur;
import org.polytech.spring.films.repository.ActeurRepository;
import org.polytech.spring.films.repository.FilmRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActeurService {
    private static final Set<String> tris = Set.of("id", "firstname", "name");

    private final ActeurRepository acteurRepository;
    private final FilmRepository filmRepository;

    public ActeurService(ActeurRepository acteurRepository, FilmRepository filmRepository) {
        this.acteurRepository = acteurRepository;
        this.filmRepository = filmRepository;
    }

    @Transactional(readOnly = true)
    public PageDto<ActeurDto> getActeurs(String recherche, int page, int size, String sort) {
        if (page < 0 || size < 1 || size > 100) {
            throw new InvalidActeurException("Invalid pagination parameters");
        }
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        String motif = (recherche == null || recherche.isBlank())
                ? "%"
                : "%" + recherche.toLowerCase() + "%";
        return PageDto.of(acteurRepository.search(motif, pageable).map(ActeurMapper::toDto));
    }

    private Sort parseSort(String sort) {
        String[] parts = sort.split(",");
        String champ = parts[0].trim();
        if (!tris.contains(champ) || parts.length > 2) {
            throw new InvalidActeurException("Invalid sort field: " + sort);
        }
        Sort.Direction dir = Sort.Direction.ASC;
        if (parts.length == 2) {
            dir = Sort.Direction.fromOptionalString(parts[1].trim())
                    .orElseThrow(() -> new InvalidActeurException("Invalid sort direction: " + parts[1]));
        }
        return Sort.by(dir, champ);
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
