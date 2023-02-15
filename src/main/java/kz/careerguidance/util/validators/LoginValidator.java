package kz.careerguidance.util.validators;


import kz.careerguidance.dto.requests.LoginRequestDTO;
import kz.careerguidance.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    private final AuthenticationService authenticationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequestDTO person = (LoginRequestDTO) target;
        if (authenticationService.getByUsername(person.getUsername()).isEmpty()) {
            errors.rejectValue("username", "", "User with such username doesn't exists");
        }

    }
}
