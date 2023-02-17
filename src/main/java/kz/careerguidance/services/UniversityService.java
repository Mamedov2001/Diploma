package kz.careerguidance.services;

import kz.careerguidance.models.University;
import kz.careerguidance.repositories.UniversityRepository;
import kz.careerguidance.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    @Transactional
    public University save(University university) {
        return universityRepository.save(university);
    }

    @Transactional
    public void delete(Long id) {
        universityRepository.deleteById(id);
    }

    public University findById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("University not found"));
    }

    public University findByName(String name) {
        return universityRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("University not found"));
    }

    public Optional<List<University>> findAll() {
        return Optional.of(universityRepository.findAll());
    }

    public Optional<University> findBySpeciality(String speciality) {
        return Optional.of(universityRepository.findBySpeciality(speciality)
                .orElseThrow(() -> new NotFoundException("University not found")));
    }

    public Optional<University> findByFaculty(String faculty) {
        return Optional.of(universityRepository.findByFaculty(faculty)
                .orElseThrow(() -> new NotFoundException("University not found")));
    }

    public Optional<University> searchByName(String query) {
        return Optional.ofNullable(universityRepository.findByNameStartingWith(query)
                .orElseThrow(() -> new NotFoundException("University not found")));
    }

}
