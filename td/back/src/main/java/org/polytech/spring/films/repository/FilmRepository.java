package org.polytech.spring.films.repository;

import java.util.List;
import java.util.Optional;

import org.polytech.spring.films.model.Film;
import org.polytech.spring.films.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmRepository extends JpaRepository<Film, Long> {

        List<Film> findByRolesActeurIdOrderByTitre(Long acteurId);

        @Query("""
                        select f from Film f join f.roles r
                        where r.acteur.id = :acteurId
                        order by f.titre
                        """)
        List<Film> findFilmsOfActeur(@Param("acteurId") Long acteurId);

        @Query("""
                        select f from Film f
                        left join fetch f.roles r
                        left join fetch r.acteur
                        where f.id = :id
                        """)
        Optional<Film> findByIdWithActeurs(@Param("id") Long id);

        @Query("""
                        select f from Film f
                        where lower(f.realisateur) like :motif and (:genre is null or f.genre = :genre)
                        """)
        Page<Film> search(@Param("motif") String motif, @Param("genre") Genre genre, Pageable pageable);
}
