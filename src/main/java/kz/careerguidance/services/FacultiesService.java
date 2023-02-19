package kz.careerguidance.services;
import kz.careerguidance.dto.requests.SpecialityDTO;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.repositories.FacultiesRepository;
import kz.careerguidance.repositories.SpecialitiesRepository;
import kz.careerguidance.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacultiesService {
    private final FacultiesRepository facultiesRepository;
    private final SpecialitiesRepository specialitiesRepository;

    @Transactional
    public void save (Faculty faculty) {
        facultiesRepository.save(faculty);
    }



    @Transactional
    public void delete (Long id) {
        if (facultiesRepository.existsById(id)) {
            List<Speciality> specialitiesToChange = specialitiesRepository.findByFacultyId(id);
            specialitiesToChange.forEach(speciality -> speciality.setFaculty(null));
            specialitiesRepository.saveAll(specialitiesToChange);
//            specialitiesRepository.save(specialitiesRepository.findByFacultyId(id).forEach(faculty -> faculty.setFaculty(null)));
            facultiesRepository.deleteById(id);
        }
        else {
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
        List<Faculty> facultyList = facultiesRepository.findAll();
        if (facultyList.isEmpty()) {
            throw new NotFoundException("Faculties not found");
        }
        else{
            return facultyList;
        }
    }


//    @Transactional
//    public void addSpeciality(Long facultyId, Long specialityId) {
//
//        Faculty faculty = facultiesRepository.findById(facultyId).orElseThrow(() -> new NotFoundException("Faculty not found"));
//        Speciality speciality = specialitiesRepository.findById(specialityId).orElseThrow(() -> new NotFoundException("Speciality not found"));
//
//        faculty.getSpecialities().addAll(faculty
//                .getSpecialities()
//                .stream()
//                .map(s -> {
//                    speciality.setFaculty(faculty);
//                    return speciality;
//                }).collect(Collectors.toList()));
//        facultiesRepository.save(faculty);
//        specialitiesRepository.save(speciality);
//    }
}
