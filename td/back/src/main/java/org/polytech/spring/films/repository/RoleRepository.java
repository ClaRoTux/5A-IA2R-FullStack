package org.polytech.spring.films.repository;

import java.util.Optional;

import org.polytech.spring.films.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

        Optional<Role> findByFilmIdAndActeurId(Long filmId, Long acteurId);
}
