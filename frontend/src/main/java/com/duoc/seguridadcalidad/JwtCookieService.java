package com.duoc.seguridadcalidad;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class JwtCookieService {

    public static final String COOKIE_NAME = "AUTH_TOKEN";

    private final boolean secure;
    private final long maxAgeSeconds;
    private final String sameSite;

    public JwtCookieService(
            @Value("${app.security.jwt-cookie.secure:true}") boolean secure,
            @Value("${app.security.jwt-cookie.max-age-seconds:3600}") long maxAgeSeconds,
            @Value("${app.security.jwt-cookie.same-site:Strict}") String sameSite
    ) {
        this.secure = secure;
        this.maxAgeSeconds = maxAgeSeconds;
        this.sameSite = sameSite;
    }

    public ResponseCookie createAuthCookie(String token) {
        return ResponseCookie.from(COOKIE_NAME, token)
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path("/")
                .maxAge(maxAgeSeconds)
                .build();
    }

    public ResponseCookie clearAuthCookie() {
        return ResponseCookie.from(COOKIE_NAME, "")
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path("/")
                .maxAge(0)
                .build();
    }

    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isBlank()) {
                return cookie.getValue();
            }
        }

        return null;
    }
}