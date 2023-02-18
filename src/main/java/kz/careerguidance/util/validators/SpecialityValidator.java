package kz.careerguidance.util.validators;

import kz.careerguidance.dto.requests.SpecialityDTO;
import kz.careerguidance.models.Speciality;
import kz.careerguidance.repositories.SpecialitiesRepository;
import kz.careerguidance.services.SpecialitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SpecialityValidator implements Validator {
    private final SpecialitiesService specialitiesService;
    @Override
    public boolean supports(Class<?> clazz) {
        return SpecialityDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpecialityDTO speciality = (SpecialityDTO) target;

        if (specialitiesService.findByCode(speciality.getCode()).isPresent()) {
            errors.rejectValue("code", "", "Speciality with such code is already exists");
        }
        if (specialitiesService.findByName(speciality.getName()).isPresent()) {
            errors.rejectValue("name", "", "Speciality with such name is already exists");
        }
    }
}
