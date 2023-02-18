package kz.careerguidance.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.careerguidance.models.Faculty;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Faculty} entity
 */
@Data
public class FacultyDTO implements Serializable {
    @NotNull(message = "Faculty name cannot be null")
    @Size(min = 3, max = 100, message = "Faculty name must contain between 3 and 100 characters")
    private final String name;
    @NotNull(message = "Faculty description cannot be null")
    @Size(min = 20, max = 200, message = "Faculty description must contain between 20 and 200 characters")
    private final String description;
}