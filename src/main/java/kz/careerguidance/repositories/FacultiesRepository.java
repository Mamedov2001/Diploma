package kz.careerguidance.repositories;

import kz.careerguidance.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultiesRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByName(String name);
}
