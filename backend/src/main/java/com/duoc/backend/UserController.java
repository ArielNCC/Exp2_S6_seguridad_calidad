package com.duoc.backend;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Autenticación", description = "Operaciones de autenticación y gestión segura de usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea un usuario en la base de datos con la contraseña almacenada de forma segura usando BCrypt",
        security = { @SecurityRequirement(name = "bearer") }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario registrado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o usuario duplicado",
            content = @Content(schema = @Schema(example = "{\"message\":\"Username already exists\"}"))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Se requiere autenticación JWT válida",
            content = @Content
        )
    })
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.registerUser(request);
            UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}