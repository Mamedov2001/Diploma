package kz.careerguidance.dto.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.careerguidance.models.role.Role;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequestDTO implements Serializable {
  @NotNull(message = "Имя не должно быть пустым")
  @Size(min = 2, max = 20, message = "Имя должно содержать между 2 и 20 символами")
  private String username;

  @Email(message = "Введите корректный email адрес")
  @NotNull(message = "Email не должен быть пустым")
  private String email;

  @NotNull(message = "Пароль не должен быть пустым")
  @Size(min = 8, max = 16, message = "Пароль должен иметь длину не менее 8 символов")
  private String password;

  @NotNull(message = "Роль не может быть пустым")
  @Enumerated(EnumType.STRING)
  private Role role;
}
