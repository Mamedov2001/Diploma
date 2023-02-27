package kz.careerguidance.controllers;

import jakarta.validation.Valid;
import kz.careerguidance.dto.requests.UniversityDTO;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.models.University;
import kz.careerguidance.services.FacultiesService;
import kz.careerguidance.services.UniversitiesService;
import kz.careerguidance.util.exceptions.NotFoundException;
import kz.careerguidance.util.validators.UniversityValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static kz.careerguidance.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/universities")
public class UniversitiesController {
    private final UniversitiesService universitiesService;
    private final FacultiesService facultiesService;
    private final ModelMapper modelMapper;
    private final UniversityValidator universityValidator;

    @GetMapping
    public ResponseEntity<List<University>> getAllUniversities() {
        return new ResponseEntity<>(universitiesService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long id) {
        return ResponseEntity.ok(universitiesService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> createUniversity(@RequestBody @Valid UniversityDTO universityDTO, BindingResult bindingResult) {
        universityValidator.validate(convertToUniversity(universityDTO), bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        universitiesService.save(convertToUniversity(universityDTO));
        return new ResponseEntity<>(HttpStatus.CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<University>> searchUniversitiesStartingWith(@RequestParam String name) {
        return new ResponseEntity<>(universitiesService.findByNameContaining(name), HttpStatus.FOUND);
    }

    @PatchMapping("{id}")
    public ResponseEntity<HttpStatus> updateUniversity(@PathVariable Long id,
                                                       @RequestBody @Valid UniversityDTO universityDTO,
                                                       BindingResult bindingResult) {
        universityValidator.validate(convertToUniversity(universityDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

            University universityToChange = universitiesService.findById(id);

            universityToChange.setName(universityDTO.getName());
            universityToChange.setDescription(universityDTO.getDescription());
            universityToChange.setAddress(universityDTO.getAddress());
            universityToChange.setCity(universityDTO.getCity());
            universityToChange.setRating(universityDTO.getRating());
            universityToChange.setLink(universityDTO.getLink());

            universitiesService.save(universityToChange);


        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUniversity(@PathVariable Long id) {
        universitiesService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/addFaculty")
    public ResponseEntity<HttpStatus> addFaculty(@PathVariable Long id, @RequestParam Long facultyId) {
        Faculty faculty = facultiesService.findById(facultyId);
        University university = universitiesService.findById(id);
        university.getFaculties().add(faculty);

        universitiesService.save(university);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public University convertToUniversity(UniversityDTO universityDTO) {
        return modelMapper.map(universityDTO, University.class);
    }
}
