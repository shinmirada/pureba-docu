package com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // La clave secreta se inyecta desde application.properties
    // Debe tener al menos 512 bits para HS512 (64 caracteres o más)
    @Value("${app.jwt.secret:SuperSecretKeyThatNeedsToBeVeryLongToWorkWithHS512123456789012345678901234567890}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms:86400000}") // 24 horas por defecto
    private int jwtExpirationInMs;

    /**
     * Genera un token JWT con los datos del usuario.
     *
     * @param userId         ID del usuario
     * @param organizationId ID de la organización (tenant)
     * @param role           Rol del usuario (ADMIN o USER)
     * @return Token JWT como String
     */
    public String generateToken(Long userId, Long organizationId, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(userId.toString()) // "sub": userId
                .claim("organizationId", organizationId) // claim personalizado
                .claim("role", role) // claim personalizado
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Obtiene el userId almacenado en el token (subject).
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * Obtiene el organizationId del token.
     */
    public Long getOrganizationIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("organizationId", Long.class);
    }

    /**
     * Obtiene el role del token.
     */
    public String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role", String.class);
    }

    /**
     * Valida si el token es correcto (firma, expiración, etc.)
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extrae los claims (payload) del token usando la clave secreta.
     */
    private Claims getClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}