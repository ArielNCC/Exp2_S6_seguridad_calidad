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
@RequestMapping("/api/appointments")
public class AppointmentRestController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentRestController.class);
    private final BackendService backendService;
    private final JwtCookieService jwtCookieService;

    public AppointmentRestController(BackendService backendService, JwtCookieService jwtCookieService) {
        this.backendService = backendService;
        this.jwtCookieService = jwtCookieService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll(HttpServletRequest request) {
        String token = jwtCookieService.extractToken(request);
        log.debug("GET /api/appointments tokenPresent={}", token != null);
        if (token == null) {
            log.warn("GET /api/appointments missing authentication token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Map<String, Object>> appointments = backendService.getAppointments(token);
        log.debug("GET /api/appointments returning {} appointments", appointments.size());
        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(HttpServletRequest request,
                                             @RequestBody Map<String, Object> appointment) {
        String token = jwtCookieService.extractToken(request);
        log.debug("POST /api/appointments tokenPresent={} payload={}", token != null, appointment);
        if (token == null) {
            log.warn("POST /api/appointments missing authentication token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, Object> saved = backendService.createAppointment(token, appointment);
        log.debug("POST /api/appointments saved={}", saved);
        return ResponseEntity.ok(saved);
    }
}
