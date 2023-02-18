package kz.careerguidance.repositories;

import kz.careerguidance.models.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialitiesRepository extends JpaRepository<Speciality, Long> {
    Optional<Speciality> findByCode(String specialtyCode);

    Optional<Speciality> findByName(String name);
}
