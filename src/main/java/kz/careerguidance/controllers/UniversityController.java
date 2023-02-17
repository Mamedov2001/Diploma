package kz.careerguidance.controllers;

import kz.careerguidance.models.University;
import kz.careerguidance.services.UniversityService;
import kz.careerguidance.util.NotFoundException;
import kz.careerguidance.util.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static kz.careerguidance.util.ErrorsUtil.exceptionHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/universities")
public class UniversityController {
    private final UniversityService universityService;
    @GetMapping
    public Optional<List<University>> getAllUniversities() {
        return universityService.findAll();
    }

    @GetMapping("/{name}")
    public University getUniversityByName(@PathVariable String name) {
        return universityService.findByName(name);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handler(NotFoundException ex) {
        return exceptionHandler(ex);
    }

}
