package com.apirest.wwapi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.UsuarioService;

// Controlador para el perfil del usuario
@RestController
@RequestMapping("/api/v1/wwdemo/")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<Usuario> getProfile(Principal principal) {
        // Obtenemos el email del usuario autenticado desde el token JWT
        String email = principal.getName();

        // Buscamos el usuario por su email
        Usuario usuario = usuarioService.findByEmail(email);

        // Retornamos el perfil del usuario
        return ResponseEntity.ok(usuario);
    }
}
