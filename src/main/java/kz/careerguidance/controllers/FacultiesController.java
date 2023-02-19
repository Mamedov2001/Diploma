package kz.careerguidance.controllers;


import jakarta.validation.Valid;
import kz.careerguidance.dto.requests.FacultyDTO;
import kz.careerguidance.dto.requests.SpecialityDTO;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.services.FacultiesService;
import kz.careerguidance.services.SpecialitiesService;
import kz.careerguidance.util.exceptions.NotFoundException;
import kz.careerguidance.util.validators.FacultyValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kz.careerguidance.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faculties")
public class FacultiesController {
    private final FacultiesService facultiesService;
    private final SpecialitiesService specialitiesService;
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

    @PostMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> updateFaculty(@PathVariable Long id,
                                                  @RequestBody @Valid FacultyDTO facultyDTO,
                                                  BindingResult bindingResult) {
        facultyValidator.validate(facultyDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        if (facultiesService.findById(id) != null) {
            Faculty facultyToChange = facultiesService.findById(id);

            facultyToChange.setName(facultyDTO.getName());
            facultyToChange.setDescription(facultyDTO.getDescription());

            facultiesService.save(facultyToChange);
        }
        else {
            throw new NotFoundException("Faculty with id: " + id + " not found");
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteUniversity(@PathVariable Long id) {
        facultiesService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{facultyId}/addSpeciality")
    public ResponseEntity<HttpStatus> addSpeciality(@PathVariable Long facultyId,
                                                    @RequestParam Long specialityId) {

        Faculty faculty = facultiesService.findById(facultyId);
        List<Speciality> specialities = faculty.getSpecialities();
        specialities.add(specialitiesService.findById(specialityId));
        faculty.setSpecialities(specialities);
        Speciality speciality = specialitiesService.findById(specialityId);
        speciality.setFaculty(faculty);
        specialitiesService.save(speciality);
        facultiesService.save(faculty);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    public Faculty convertToFaculty(FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }
    public Speciality convertToSpeciality(SpecialityDTO specialityDTO) {
        return modelMapper.map(specialityDTO, Speciality.class);
    }

}
