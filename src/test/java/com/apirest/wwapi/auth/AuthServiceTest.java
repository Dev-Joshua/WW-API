package com.apirest.wwapi.auth;

import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.UsuarioRepo;
import com.apirest.wwapi.Jwt.JwtService;
import com.apirest.wwapi.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepo usuarioRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setContrasena("password");
    }

    @Test
    public void testLoginSuccessful() {
        when(usuarioRepo.findByEmail("test@example.com")).thenReturn(Optional.of(usuario));
        when(jwtService.getToken(usuario)).thenReturn("jwt-token");

        AuthResponse response = authService.login(new LoginRequest("test@example.com", "password"));

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepo).findByEmail("test@example.com");
    }

    @Test
    public void testRegisterSuccessful() {
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(usuarioService.createUser(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.getToken(usuario)).thenReturn("jwt-token");

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setContrasena("password");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(passwordEncoder).encode("password");
        verify(usuarioService).createUser(any(Usuario.class));
    }
}

