package kz.careerguidance.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private Float rating;
    private String link;

    @ManyToMany
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
