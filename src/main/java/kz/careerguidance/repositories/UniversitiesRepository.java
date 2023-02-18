package kz.careerguidance.repositories;

import kz.careerguidance.models.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversitiesRepository extends JpaRepository<University, Long> {

    Optional<University> findByName(String name);
    @Query(value = "select u.* from university u join university_faculty uf on u.id = uf.university_id" +
            "         join faculty f on f.id = uf.faculty_id" +
            "         join speciality s on f.id = s.faculty_id" +
            "         where s.name = :speciality", nativeQuery = true)
    List<University> findBySpeciality(@Param("speciality") String speciality);


    // todo change this to find all universities with faculty name
    @Query(value = "select u.* from university u join university_faculty uf on u.id = uf.university_id" +
            "         join faculty f on f.id = uf.faculty_id" +
            "         join speciality s on f.id = s.faculty_id" +
            "         where s.name = :speciality", nativeQuery = true)
    List<University> findByFaculty(String speciality);

    List<University> findByNameStartingWith(String str);
}
