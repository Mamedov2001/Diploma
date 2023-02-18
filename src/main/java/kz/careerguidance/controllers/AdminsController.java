package kz.careerguidance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminsController {
    @GetMapping()
    public ResponseEntity<HttpStatus> a() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
