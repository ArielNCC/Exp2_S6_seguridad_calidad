package com.duoc.seguridadcalidad;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

    private static final Logger log = LoggerFactory.getLogger(PatientRestController.class);
    private final BackendService backendService;
    private final JwtCookieService jwtCookieService;

    public PatientRestController(BackendService backendService, JwtCookieService jwtCookieService) {
        this.backendService = backendService;
        this.jwtCookieService = jwtCookieService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll(HttpServletRequest request) {
        String token = jwtCookieService.extractToken(request);
        log.debug("GET /api/patients tokenPresent={}", token != null);
        if (token == null) {
            log.warn("GET /api/patients missing authentication token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Map<String, Object>> patients = backendService.getPatients(token);
        log.debug("GET /api/patients returning {} patients", patients.size());
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(HttpServletRequest request,
                                         @RequestBody Map<String, Object> patient) {
        String token = jwtCookieService.extractToken(request);
        log.debug("POST /api/patients tokenPresent={} payload={}", token != null, patient);
        if (token == null) {
            log.warn("POST /api/patients missing authentication token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, Object> saved = backendService.createPatient(token, patient);
        log.debug("POST /api/patients saved={}", saved);
        return ResponseEntity.ok(saved);
    }
}
