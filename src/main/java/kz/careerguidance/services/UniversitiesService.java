package kz.careerguidance.services;

import kz.careerguidance.models.University;
import kz.careerguidance.repositories.UniversitiesRepository;
import kz.careerguidance.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static kz.careerguidance.util.ErrorsUtil.returnErrorsToClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversitiesService {
    private final UniversitiesRepository universitiesRepository;

    @Transactional
    public University save(University university) {
        return universitiesRepository.save(university);
    }

    @Transactional
    public void delete(Long id) {
        if (universitiesRepository.existsById(id)) {
            universitiesRepository.deleteById(id);
        }
        else {
            throw new NotFoundException("University not found");
        }
    }

    public University findById(Long id) {
        return universitiesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("University not found"));
    }

    public Optional<University> findByName(String name) {
        return universitiesRepository.findByName(name);
    }

    public List<University> findAll() {
        return universitiesRepository.findAll();
    }

    public List<University> findBySpeciality(String speciality) {
        List<University> universityList = universitiesRepository.findBySpeciality(speciality);
        if (universityList.isEmpty()) {
            throw new NotFoundException("Universities not found");
        }
        return universityList;
    }

//    public List<University> findByFaculty(String faculty) {
//        List<University> universityList = universitiesRepository.findByFaculty(faculty);
//        if (universityList.isEmpty()) {
//            throw new NotFoundException("Universities not found");
//        }
//        return universityList;
//    }

    public List<University> findByNameContaining(String query) {
         return universitiesRepository.findByNameStartingWith(query);
    }



//    public Supplier returnException(String message) {
//        return () -> new NotFoundException(message);
//    }
}

