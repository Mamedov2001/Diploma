package kz.careerguidance.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.careerguidance.models.University;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link University} entity
 */
@Data
public class UniversityDTO implements Serializable {
    @Size(min = 5, max = 100, message = "University name must be between 5 and 100 characters")
    @NotNull(message = "University name is required")
    private final String name;
    @Size(min = 30, max = 500, message = "University description must be between 30 and 500 characters")
    @NotNull(message = "University description is required")
    private final String description;
    @Size(min = 5, max = 100, message = "University address must be between 5 and 100 characters")
    @NotNull(message = "University address is required")
    private final String address;
    @Size(min = 3, max = 100, message = "University city must be between 5 and 100 characters")
    @NotNull(message = "University city is required")
    private final String city;
    @NotNull(message = "University rating is required")
    private final Float rating;
    @NotNull(message = "University link is required")
    private final String link;
}