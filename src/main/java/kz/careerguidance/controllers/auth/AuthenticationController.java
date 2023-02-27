package kz.careerguidance.controllers.auth;

import jakarta.validation.Valid;
import kz.careerguidance.dto.requests.LoginRequestDTO;
import kz.careerguidance.dto.requests.RegisterRequestDTO;
import kz.careerguidance.dto.responses.LoginResponse;
import kz.careerguidance.models.Person;
import kz.careerguidance.services.auth.AuthenticationService;
import kz.careerguidance.util.validators.LoginValidator;
import kz.careerguidance.util.validators.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kz.careerguidance.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final RegistrationValidator registrationValidator;
  private final LoginValidator loginValidator;
  private final ModelMapper modelMapper;

  @PostMapping("/registration")
  public ResponseEntity<LoginResponse> register(
          @RequestBody @Valid RegisterRequestDTO request,
          BindingResult bindingResult
  ) {
    registrationValidator.validate(convertToPerson(request), bindingResult);
    if (bindingResult.hasErrors()) {
      returnErrorsToClient(bindingResult);
    }

    return new ResponseEntity<>(service.register(request), HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody @Valid LoginRequestDTO request,
      BindingResult bindingResult
  ) {
    loginValidator.validate(request, bindingResult);

    if (bindingResult.hasErrors()) {
      returnErrorsToClient(bindingResult);
    }

    return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
  }

  private Person convertToPerson(RegisterRequestDTO request) {
    return modelMapper.map(request, Person.class);
  }
}
