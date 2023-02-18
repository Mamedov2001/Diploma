package kz.careerguidance.services;
import kz.careerguidance.models.Faculty;
import kz.careerguidance.repositories.FacultiesRepository;
import kz.careerguidance.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacultiesService {
    private final FacultiesRepository facultiesRepository;

    @Transactional
    public void save (Faculty faculty) {
        facultiesRepository.save(faculty);
    }



    @Transactional
    public void delete (Long id) {
        if (facultiesRepository.existsById(id)) {
            facultiesRepository.deleteById(id);
        }
        else {
            throw new NotFoundException("Faculty doesn't exist");
        }
    }

    public Faculty findById (Long id) {
        return facultiesRepository.findById(id).orElseThrow(() -> new NotFoundException("Faculty not found"));
    }

    public Optional<Faculty> findByName (String name) {
        return facultiesRepository.findByName(name);
    }

    public List<Faculty> findAll () throws NotFoundException {
        List<Faculty> facultyList = facultiesRepository.findAll();
        if (facultyList.isEmpty()) {
            throw new NotFoundException("Faculties not found");
        }
        else{
            return facultyList;
        }
    }


}
