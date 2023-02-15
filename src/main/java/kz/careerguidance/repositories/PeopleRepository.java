package kz.careerguidance.repositories;

import java.util.Optional;

import kz.careerguidance.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<Person, Integer> {

  Optional<Person> findByEmail(String email);

  Optional<Person> findByUsername(String username);
}
