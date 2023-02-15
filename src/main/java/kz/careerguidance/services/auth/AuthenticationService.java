package kz.careerguidance.services.auth;

import kz.careerguidance.dto.requests.LoginRequestDTO;
import kz.careerguidance.dto.requests.RegisterRequestDTO;
import kz.careerguidance.dto.responses.LoginResponse;
import kz.careerguidance.config.JwtService;
import kz.careerguidance.models.Person;
import kz.careerguidance.repositories.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final PeopleRepository peopleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

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

  public Map<String, String> authenticate(LoginRequestDTO request) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );

    try {
      authenticationManager.authenticate(authToken);
    }
    catch (BadCredentialsException e) {
      return Map.of("message", "Incorrect password");
    }

    var user = peopleRepository.findByUsername(request.getUsername())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return Map.of("jwtToken", jwtToken);
  }

  public Optional<Person> getByUsername(String username) {
    return peopleRepository.findByUsername(username);
  }


  public Optional<Person> getByEmail(String email) {
    return peopleRepository.findByEmail(email);
  }
}
