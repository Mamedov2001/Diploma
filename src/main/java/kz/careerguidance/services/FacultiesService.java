package kz.careerguidance.services;

import kz.careerguidance.models.Faculty;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.models.University;
import kz.careerguidance.repositories.FacultiesRepository;
import kz.careerguidance.repositories.SpecialitiesRepository;
import kz.careerguidance.repositories.UniversitiesRepository;
import kz.careerguidance.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacultiesService {
    private final FacultiesRepository facultiesRepository;
    private final SpecialitiesRepository specialitiesRepository;
    private final UniversitiesRepository universitiesRepository;

    @Transactional
    public void save (Faculty faculty) {
        facultiesRepository.save(faculty);
    }



    @Transactional
    public void delete (Long id) {
            try {
                Optional<Faculty> facultyToDelete = facultiesRepository.findById(id);
                List<Speciality> specialitiesToChange = specialitiesRepository.findByFacultyId(id);
                List<University> universitiesToChange = universitiesRepository.findByFaculties(facultyToDelete.get());
                specialitiesToChange.forEach(speciality -> speciality.setFaculty(null));

                universitiesToChange.forEach(university -> university.getFaculties().remove(facultyToDelete.get()));
                universitiesRepository.saveAll(universitiesToChange);
                specialitiesRepository.saveAll(specialitiesToChange);
                facultiesRepository.deleteById(id);
            }
            catch (Exception e) {
                throw new NotFoundException("Faculty doesn't exist");
            }
    }

    public Faculty findById (Long id) {
        return facultiesRepository.findById(id).orElseThrow(() -> new NotFoundException("Faculty not found"));
    }

    public Optional<Faculty> findByName (String name) {
        return facultiesRepository.findByName(name);
    }

    public List<Faculty> findAll () throws NotFoundException {
        try {
            return facultiesRepository.findAll();
        }
        catch (Exception e){
            throw new NotFoundException("Faculties not found");
        }
    }
}
