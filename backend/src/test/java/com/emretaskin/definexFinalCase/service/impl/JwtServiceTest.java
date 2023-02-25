package com.emretaskin.definexFinalCase.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    private static final String SECRET_KEY  = "6B5970337336763979244226452948404D6251655468576D5A7134743777217A";

    @Mock
    private UserDetails userDetails;

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
    }

    @Test
    void extractUsername() {
        String username = "john.doe";
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void extractClaim() {
        String subject = "john.doe";
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        Claims claims = jwtService.extractAllClaims(token);
        assertEquals(subject, jwtService.extractClaim(token, Claims::getSubject));
        assertEquals(expiration.getTime(), jwtService.extractClaim(token, Claims::getExpiration).getTime(), 1000);
    }


    @Test
    void generateToken() {
        String username = "john.doe";
        when(userDetails.getUsername()).thenReturn(username);
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void generateTokenWithExtraClaims() {
        String username = "john.doe";
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", "John Doe");
        extraClaims.put("email", "john.doe@example.com");
        when(userDetails.getUsername()).thenReturn(username);
        String token = jwtService.generateToken(extraClaims, userDetails);
        assertNotNull(token);
        assertTrue(token.length() > 0);
        Claims claims = jwtService.extractAllClaims(token);
        assertEquals("John Doe", claims.get("name"));
        assertEquals("john.doe@example.com", claims.get("email"));
    }

    @Test
    void isTokenValid() {
        String username = "john.doe";
        when(userDetails.getUsername()).thenReturn(username);
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}