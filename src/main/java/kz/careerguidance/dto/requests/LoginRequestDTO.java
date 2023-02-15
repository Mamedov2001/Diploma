package kz.careerguidance.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {

  @NotNull(message = "Имя не должно быть пустым")
  @Size(min = 2, max = 20, message = "Имя должно содержать между 2 и 20 символами")
  private String username;

  @NotNull(message = "Пароль не должен быть пустым")
  @Size(min = 8, max = 16, message = "Пароль должен иметь длину не менее 8 символов")
  private String password;

}
