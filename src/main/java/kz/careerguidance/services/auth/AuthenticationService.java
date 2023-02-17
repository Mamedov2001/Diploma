package kz.careerguidance.services.auth;

import kz.careerguidance.dto.requests.LoginRequestDTO;
import kz.careerguidance.dto.requests.RegisterRequestDTO;
import kz.careerguidance.dto.responses.LoginResponse;
import kz.careerguidance.models.Person;
import kz.careerguidance.repositories.PeopleRepository;
import kz.careerguidance.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {
  private final PeopleRepository peopleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public LoginResponse register(RegisterRequestDTO request) {
    var user = Person.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    peopleRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return LoginResponse.builder()
        .token(jwtToken)
        .build();
  }

  public LoginResponse authenticate(LoginRequestDTO request) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );

    try {
      authenticationManager.authenticate(authToken);
    }
    catch (BadCredentialsException e) {
      throw new NotFoundException("Incorrect password");
    }

    var user = peopleRepository.findByUsername(request.getUsername())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return LoginResponse.builder()
            .token(jwtToken)
            .build();
  }

  public Optional<Person> getByUsername(String username) {
    return peopleRepository.findByUsername(username);
  }

  public Optional<Person> getByEmail(String email) {
    return peopleRepository.findByEmail(email);
  }
}
