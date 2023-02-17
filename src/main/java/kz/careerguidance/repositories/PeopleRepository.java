package kz.careerguidance.repositories;

import java.util.Optional;

import kz.careerguidance.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByEmail(String email);

  Optional<Person> findByUsername(String username);
}
