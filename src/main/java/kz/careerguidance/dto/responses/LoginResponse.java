package kz.careerguidance.dto.responses;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {

  private String token;
}

