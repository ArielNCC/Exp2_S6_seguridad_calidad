package com.duoc.seguridadcalidad;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    // La validación y gestión del token se delegan al servicio de backend.
    // Esta clase se mantiene por si en el futuro se requiere lógica de
    // decodificación simple (sin verificación de firma) para logs o UI.
}
