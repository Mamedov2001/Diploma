package kz.careerguidance.controllers;

import jakarta.validation.Valid;
import kz.careerguidance.dto.requests.SpecialityDTO;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.services.SpecialitiesService;
import kz.careerguidance.util.exceptions.NotFoundException;
import kz.careerguidance.util.validators.SpecialityValidator;
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
@RequestMapping("/specialities")
public class SpecialitiesController {
    private final SpecialitiesService specialitiesService;
    private final SpecialityValidator specialityValidator;
    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<Speciality>> getAllSpecialities() {
        return ResponseEntity.ok(specialitiesService.findAll());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createSpeciality(@RequestBody @Valid SpecialityDTO specialityDTO,
                                                    BindingResult bindingResult) {
        specialityValidator.validate(specialityDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        specialitiesService.save(convertToSpeciality(specialityDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> editSpeciality(@PathVariable Long id,
                                                  @RequestBody @Valid SpecialityDTO specialityDTO,
                                                  BindingResult bindingResult) {
        specialityValidator.validate(specialityDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }

        if (specialitiesService.findById(id) != null) {
            Speciality specialityToChange = specialitiesService.findById(id);

            specialityToChange.setCode(specialityDTO.getCode());
            specialityToChange.setName(specialityDTO.getName());
            specialityToChange.setDescription(specialityDTO.getDescription());

            specialitiesService.save(specialityToChange);
        }
        else {
            throw new NotFoundException("Speciality with id " + id + " not found");
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteSpeciality(@PathVariable Long id) {
        specialitiesService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }



    public Speciality convertToSpeciality(SpecialityDTO specialityDTO) {
        return modelMapper.map(specialityDTO, Speciality.class);
    }
}
