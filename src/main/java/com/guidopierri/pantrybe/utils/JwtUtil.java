package com.guidopierri.pantrybe.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final Key key;
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    public JwtUtil() {
        // Generate a secure key
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String extractUsernameWithKey(String token) {
        return extractClaimWithKey(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaimWithKey(token, Claims::getExpiration);
    }

    public <T> T extractClaimWithKey(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsWithKey(token);
        return claimsResolver.apply(claims);
    }

    public <T> T extractClaimWithOauth2(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsOauth2(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaimsWithKey(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Claims extractAllClaimsOauth2(String token) {
        return Jwts.parserBuilder().build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30))
                .signWith(key)  // Use the secure key
                .compact();
    }

    @Cacheable(value = "tokenValidation", key = "#token")
    public Boolean validateTokenWithKey(String token, UserDetails userDetails) {
        final String username = extractUsernameWithKey(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }
}