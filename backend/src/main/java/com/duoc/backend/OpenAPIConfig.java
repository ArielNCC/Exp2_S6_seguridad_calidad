package com.duoc.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Pet Adoption API",
        version = "1.0.0",
        description = "API RESTful para gestión de mascotas disponibles para adopción y pacientes",
        contact = @Contact(
            name = "DUOC UC - CDY2203",
            url = "https://www.duoc.cl"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Development Server"
        ),
        @Server(
            url = "https://api.example.com",
            description = "Production Server"
        )
    }
)
@SecurityScheme(
    name = "bearer",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT Token para autenticación",
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
