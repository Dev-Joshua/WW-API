/*
 package com.apirest.wwapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.UsuarioRepo;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuarioRepository; // Tu repositorio de usuarios

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email); // Método que debes implementar en tu repositorio

          Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getAuthorities() // Asegúrate de tener un método para obtener las autoridades del usuario
        );
    }
}
 */