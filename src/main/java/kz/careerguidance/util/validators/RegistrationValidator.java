package kz.careerguidance.util.validators;


import kz.careerguidance.dto.requests.RegisterRequestDTO;
import kz.careerguidance.models.Person;
import kz.careerguidance.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RegistrationValidator implements Validator {

    private final AuthenticationService authenticationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (authenticationService.getByUsername(person.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "User with such username already is exists");
        }
        if (authenticationService.getByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "User with such email already is exists");
        }
    }
}
