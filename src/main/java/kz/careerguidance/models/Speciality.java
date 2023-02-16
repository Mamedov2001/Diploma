package kz.careerguidance.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Speciality name cannot be null")
    @Size(min = 3, max = 20, message = "Speciality name should be between 3 and 20 characters")
    private String name;

    @NotNull(message = "Speciality description cannot be null")
    @Size(min = 20, max = 200, message = "Speciality description should be between 20 and 200 characters")
    private String description;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    public Speciality(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
