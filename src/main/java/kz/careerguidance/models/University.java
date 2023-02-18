package kz.careerguidance.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 100, message = "University name must be between 5 and 100 characters")
    @NotNull(message = "University name is required")
    private String name;

    @Size(min = 30, max = 500, message = "University description must be between 30 and 500 characters")
    @NotNull(message = "University description is required")
    private String description;

    @Size(min = 5, max = 100, message = "University address must be between 5 and 100 characters")
    @NotNull(message = "University address is required")
    private String address;

    @Size(min = 5, max = 100, message = "University city must be between 5 and 100 characters")
    @NotNull(message = "University city is required")
    private String city;

    @NotNull(message = "University rating is required")
    private Float rating;

    @NotNull(message = "University link is required")
    private String link;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "university_faculty",
            joinColumns = @JoinColumn(name = "university_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private List<Faculty> faculties;

    public University(String name,
                      String description,
                      String address,
                      String city,
                      Float rating,
                      String link) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.rating = rating;
        this.link = link;
    }
}
