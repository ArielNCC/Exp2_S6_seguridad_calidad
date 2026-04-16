package com.duoc.seguridadcalidad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final BackendService backendService;
    private final JwtCookieService jwtCookieService;

    public AuthController(BackendService backendService, JwtCookieService jwtCookieService) {
        this.backendService = backendService;
        this.jwtCookieService = jwtCookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        log.debug("POST /api/auth/login req={}", authRequest);
        try {
            AuthResponse authResponse = backendService.login(authRequest);
            log.debug("POST /api/auth/login authentication succeeded");
            return ResponseEntity.noContent()
                    .header(HttpHeaders.SET_COOKIE, jwtCookieService.createAuthCookie(authResponse.getToken()).toString())
                    .build();
        } catch (HttpStatusCodeException ex) {
            // El backend respondió con un error específico (ej: 401 Credenciales inválidas)
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            // No se pudo conectar al backend (Backend down o URL incorrecta)
            log.error("Error de conexión con el backend: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("El servicio de backend no está disponible.");
        } catch (Exception ex) {
            log.error("POST /api/auth/login error", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, jwtCookieService.clearAuthCookie().toString())
                .build();
    }

    @GetMapping("/session")
    public ResponseEntity<Void> session(HttpServletRequest request) {
        return jwtCookieService.extractToken(request) == null
                ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                : ResponseEntity.noContent().build();
    }
}
