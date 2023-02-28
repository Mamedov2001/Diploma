package kz.careerguidance.services;

import kz.careerguidance.dto.requests.FacultyDTO;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.models.University;
import kz.careerguidance.repositories.FacultiesRepository;
import kz.careerguidance.repositories.SpecialitiesRepository;
import kz.careerguidance.repositories.UniversitiesRepository;
import kz.careerguidance.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacultiesService {
    private final FacultiesRepository facultiesRepository;
    private final SpecialitiesRepository specialitiesRepository;
    private final UniversitiesRepository universitiesRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void save (Faculty faculty) {
        facultiesRepository.save(faculty);
    }

    @Transactional
    public void save(Long id, FacultyDTO facultyDTO) {
        Faculty faculty = convertToFaculty(facultyDTO);
        faculty.setId(facultiesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Faculty not found")).getId());
        facultiesRepository.save(faculty);
        }

    @Transactional
    public void save (Long facultyId, Long specialityId) {
                Faculty faculty = facultiesRepository.findById(facultyId)
                        .orElseThrow(
                                () -> new NotFoundException("Faculty with id: " + facultyId + " not found")
                        );

                Speciality speciality = specialitiesRepository.findById(specialityId)
                        .orElseThrow(
                                () -> new NotFoundException("Speciality with id: " + specialityId + " not found")
                        );

                faculty.getSpecialities().stream()
                        .filter(s -> s.getId() == speciality.getId())
                        .findFirst()
                        .ifPresentOrElse(
                                s -> {
                                },
                                () -> {
                                    speciality.setFaculty(faculty);
                                    faculty.getSpecialities().add(speciality);
                                    specialitiesRepository.save(speciality);
                                    facultiesRepository.save(faculty);
                                }
                        );
            }



    @Transactional
    public void delete (Long id) {
        facultiesRepository.findById(id).map(facultyToDelete -> {
                    List<Speciality> specialitiesToChange = specialitiesRepository.findByFacultyId(id);
                    List<University> universitiesToChange = universitiesRepository.findByFaculties(facultyToDelete);

                    specialitiesToChange.forEach(speciality -> speciality.setFaculty(null));
                    universitiesToChange.forEach(university -> university.getFaculties().remove(facultyToDelete));

                    universitiesRepository.saveAll(universitiesToChange);
                    specialitiesRepository.saveAll(specialitiesToChange);
                    facultiesRepository.deleteById(id);
                    return facultyToDelete;
                })
                .orElseThrow(() -> new NotFoundException("Faculty not found"));

    }

    public Faculty findById (Long id) {
        return facultiesRepository.findById(id).orElseThrow(() -> new NotFoundException("Faculty not found"));
    }

    public Faculty findByName (String name) {
        return facultiesRepository.findByName(name).orElseThrow(() -> new NotFoundException("Faculty not found"));
    }

    public List<Faculty> findAll () throws NotFoundException {
            return facultiesRepository.findAll();
    }

    private Faculty convertToFaculty (FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }
}
