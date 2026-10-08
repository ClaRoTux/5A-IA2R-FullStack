package org.polytech.spring.films.repository;

import java.util.List;
import java.util.Optional;

import org.polytech.spring.films.model.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActeurRepository extends JpaRepository<Acteur, Long> {

        List<Acteur> findByRolesFilmIdOrderByName(Long filmId);

        @Query("""
                        select a from Acteur a join a.roles r
                        where r.film.id = :filmId
                        order by a.name
                        """)
        List<Acteur> findActeursOfFilm(@Param("filmId") Long filmId);

        @Query("""
                        select a from Acteur a
                        left join fetch a.roles r
                        left join fetch r.film
                        where a.id = :id
                        """)
        Optional<Acteur> findByIdWithFilms(@Param("id") Long id);

        @Query("""
                        select a from Acteur a
                        where lower(a.name) like :motif or lower(a.firstname) like :motif
                        """)
        Page<Acteur> search(@Param("motif") String motif, Pageable pageable);
}
