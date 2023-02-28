package kz.careerguidance.util.validators;

import kz.careerguidance.dto.requests.FacultyDTO;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.services.FacultiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FacultyValidator implements Validator {
    private final FacultiesService facultiesService;

    @Override
    public boolean supports(Class<?> clazz) {
        return FacultyDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FacultyDTO faculty = (FacultyDTO) target;

        if(facultiesService.findByName(faculty.getName()) != null) {
            errors.rejectValue("name", "", "Faculty name already exists");
        }
    }
}
