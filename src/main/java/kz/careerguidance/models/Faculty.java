package kz.careerguidance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Faculty name cannot be null")
    @Size(min = 3, max = 20, message = "Faculty name must")
    private String name;

    @NotNull(message = "Faculty description cannot be null")
    @Size(min = 3, max = 200, message = "Faculty description must")
    private String description;

    @ManyToMany(mappedBy = "faculties")
    private List<University> universities;

    @OneToMany(mappedBy = "faculty")
    private List<Speciality> specialities;

    public Faculty(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
