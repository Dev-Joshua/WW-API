package com.apirest.wwapi.Jwt;

import com.apirest.wwapi.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private Usuario usuario;
    private String token;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setId_usuario(1);
        token = jwtService.getToken(usuario);
    }

    @Test
    public void testGetToken() {
        assertNotNull(token);
        assertTrue(token.length() > 10);
    }

    @Test
    public void testGetEmailFromToken() {
        String email = jwtService.getEmailFromToken(token);
        assertEquals("test@example.com", email);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    public void testTokenExpiration() {
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired); // Assuming the token has not expired yet
    }
}
