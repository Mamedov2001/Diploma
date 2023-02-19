package kz.careerguidance.services;

import kz.careerguidance.models.Speciality;
import kz.careerguidance.repositories.SpecialitiesRepository;
import kz.careerguidance.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpecialitiesService {
    private final SpecialitiesRepository specialitiesRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void save(Speciality speciality) {
        specialitiesRepository.save(speciality);
    }

    @Transactional
    public void update(Long id, Speciality speciality) {
        if (specialitiesRepository.existsById(id)) {
            Speciality sp = new Speciality();

            sp.setId(id);
            sp.setCode(speciality.getCode());
            sp.setName(speciality.getName());
            sp.setDescription(speciality.getDescription());
            sp.setFaculty(speciality.getFaculty());

            specialitiesRepository.save(sp);
        }
        else {
            throw new NotFoundException("Speciality not found");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (specialitiesRepository.existsById(id)) {
            specialitiesRepository.deleteById(id);
        }
        else {
            throw new NotFoundException("Speciality not found");
        }
    }

    public Speciality findById(Long id) {
        return specialitiesRepository.findById(id).orElse(null);
    }

    public List<Speciality> findAll() {
        return specialitiesRepository.findAll();
    }


    public Optional<Speciality> findByName(String name) {
        return specialitiesRepository.findByName(name);
    }

    public Optional<Speciality> findByCode(String code) {
        return specialitiesRepository.findByCode(code);
    }

    public void addFaculty(Long id, Long idFaculty) {

    }
}
