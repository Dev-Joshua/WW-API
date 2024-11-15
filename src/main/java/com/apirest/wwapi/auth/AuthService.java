package com.apirest.wwapi.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.apirest.wwapi.Jwt.JwtService;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.model.Usuario.Role;
import com.apirest.wwapi.repository.UsuarioRepo;
import com.apirest.wwapi.service.SolicitudService;
import com.apirest.wwapi.service.UsuarioService;

import lombok.RequiredArgsConstructor;

// Servicio para autenticacion
@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudService.class);

    private final UsuarioRepo usuarioRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    

    public AuthResponse login(LoginRequest request) {
        try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales inválidas"); 
        }

        UserDetails usuario = usuarioRepo.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(usuario);
    
        return AuthResponse.builder().token(token).build();
    }
    
    public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .foto_usuario(request.getFoto_usuario())
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .documento_identidad(request.getDocumento_identidad())
                .ciudad(request.getCiudad())
                .direccion(request.getDireccion())
                .celular(request.getCelular())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode( request.getContrasena()))
                .rol(request.getRol())
                .build();

                if (usuario.getFoto_usuario() == null || usuario.getFoto_usuario().isEmpty()) {
                    usuario.setFoto_usuario("https://st4.depositphotos.com/11574170/25191/v/450/depositphotos_251916955-stock-illustration-user-glyph-color-icon.jpg");
                }
                
                Usuario nuevoUsuario = usuarioService.createUser(usuario);

                return AuthResponse.builder()   // Usar el usuario guardado para generar el token
                    .token(jwtService.getToken(nuevoUsuario))
                    .build();
    }
}
