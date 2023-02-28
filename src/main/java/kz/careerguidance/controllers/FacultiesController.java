package kz.careerguidance.controllers;


import jakarta.validation.Valid;
import kz.careerguidance.dto.requests.FacultyDTO;
import kz.careerguidance.dto.requests.SpecialityDTO;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.models.University;
import kz.careerguidance.services.FacultiesService;
import kz.careerguidance.services.SpecialitiesService;
import kz.careerguidance.services.UniversitiesService;
import kz.careerguidance.util.exceptions.NotFoundException;
import kz.careerguidance.util.validators.FacultyValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static kz.careerguidance.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faculties")
public class FacultiesController {
    private final FacultiesService facultiesService;
    private final SpecialitiesService specialitiesService;
    private final UniversitiesService universitiesService;
    private final FacultyValidator facultyValidator;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultiesService.findAll());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createFaculty(@RequestBody @Valid FacultyDTO facultyDTO,
                                                    BindingResult bindingResult) {
        facultyValidator.validate(facultyDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        facultiesService.save(convertToFaculty(facultyDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateFaculty(@PathVariable Long id,
                                                  @RequestBody @Valid FacultyDTO facultyDTO,
                                                  BindingResult bindingResult) {
        facultyValidator.validate(facultyDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        facultiesService.save(id, facultyDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUniversity(@PathVariable Long id) {
        facultiesService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/addSpeciality")
    public ResponseEntity<HttpStatus> addSpeciality(@PathVariable Long id,
                                                    @RequestParam Long specialityId) {
        facultiesService.save(id, specialityId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/addUniversity")
    public ResponseEntity<HttpStatus> addUniversity(@PathVariable Long id,
                                                    @RequestParam Long universityId) {
        universitiesService.save(id, universityId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Faculty convertToFaculty(FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }
    public Speciality convertToSpeciality(SpecialityDTO specialityDTO) {
        return modelMapper.map(specialityDTO, Speciality.class);
    }

}
