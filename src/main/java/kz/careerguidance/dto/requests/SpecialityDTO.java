package kz.careerguidance.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link kz.careerguidance.models.Speciality} entity
 */
@Data
public class SpecialityDTO implements Serializable {
    @NotNull(message = "Code is required")
    @Size(min = 8, max = 8, message = "Code must be 8 characters long")
    private final String code;
    @NotNull(message = "Speciality name cannot be null")
    @Size(min = 3, max = 20, message = "Speciality name should be between 3 and 20 characters")
    private final String name;
    @NotNull(message = "Speciality description cannot be null")
    @Size(min = 20, max = 200, message = "Speciality description should be between 20 and 200 characters")
    private final String description;

    private final FacultyDTO faculty;
}