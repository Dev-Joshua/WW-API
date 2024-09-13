package com.apirest.wwapi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.UsuarioRepo;

import jakarta.transaction.Transactional;

@Component
public class PasswordUpdater implements CommandLineRunner {

    private final UsuarioRepo usuarioRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordUpdater(UsuarioRepo usuarioRepo, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (Usuario usuario : usuarioRepo.findAll()) {
            if (!isPasswordEncoded(usuario.getContrasena())) {
                String encodedPassword = passwordEncoder.encode(usuario.getContrasena());
                usuario.setContrasena(encodedPassword);
                usuarioRepo.save(usuario);
                System.out.println("Updated password for user: " + usuario.getEmail());
            }
        }
    }

    private boolean isPasswordEncoded(String password) {

        return password.startsWith("{bcrypt}");
    }
}
