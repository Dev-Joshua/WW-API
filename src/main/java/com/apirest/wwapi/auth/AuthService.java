package com.apirest.wwapi.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// import org.springframework.core.userdetails.UserDetails;

import com.apirest.wwapi.Jwt.JwtService;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.model.Usuario.Role;
import com.apirest.wwapi.repository.UsuarioRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepo usuarioRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena()));
        UserDetails usuario = usuarioRepo.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(usuario);
        
        return AuthResponse.builder().token(token).build();
    }
    
     public AuthResponse register(RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .documento_identidad(request.getDocumento_identidad())
                .direccion(request.getDireccion())
                .celular(request.getCelular())
                .email(request.getEmail())
                .contrasena(passwordEncoder.encode( request.getContrasena()))
                .rol(Role.CLIENTE)
                .build();

                usuarioRepo.save(usuario);

                return AuthResponse.builder()
                    .token(jwtService.getToken(usuario))
                    .build();
    }
}
