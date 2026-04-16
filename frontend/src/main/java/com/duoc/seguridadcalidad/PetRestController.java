package com.duoc.seguridadcalidad;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
public class PetRestController {

    private static final Logger log = LoggerFactory.getLogger(PetRestController.class);
    private final BackendService backendService;
    private final JwtCookieService jwtCookieService;

    public PetRestController(BackendService backendService, JwtCookieService jwtCookieService) {
        this.backendService = backendService;
        this.jwtCookieService = jwtCookieService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        log.debug("GET /api/pets");
        List<Map<String, Object>> pets = backendService.getPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Map<String, Object>>> getAvailable() {
        log.debug("GET /api/pets/available");
        List<Map<String, Object>> pets = backendService.getAvailablePets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> search(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String status
    ) {
        log.debug("GET /api/pets/search species={} gender={} location={} age={} status={}", species, gender, location, age, status);
        List<Map<String, Object>> pets = backendService.searchPets(species, gender, location, age, status);
        return ResponseEntity.ok(pets);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            HttpServletRequest request,
            @RequestBody Map<String, Object> pet
    ) {
        String token = jwtCookieService.extractToken(request);
        log.debug("POST /api/pets tokenPresent={} payload={}", token != null, pet);
        if (token == null) {
            log.warn("POST /api/pets missing authentication token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> saved = backendService.createPet(token, pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            HttpServletRequest request,
            @RequestBody Map<String, Object> pet
    ) {
        String token = jwtCookieService.extractToken(request);
        log.debug("PUT /api/pets/{} tokenPresent={} payload={}", id, token != null, pet);
        if (token == null) {
            log.warn("PUT /api/pets/{} missing authentication token", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> updated = backendService.updatePet(token, id, pet);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        String token = jwtCookieService.extractToken(request);
        log.debug("DELETE /api/pets/{} tokenPresent={}", id, token != null);
        if (token == null) {
            log.warn("DELETE /api/pets/{} missing authentication token", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> deleted = backendService.deletePet(token, id);
        return ResponseEntity.ok(deleted);
    }
}
