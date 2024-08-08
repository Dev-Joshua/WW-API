package com.apirest.wwapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.ServicioRepo;
import com.apirest.wwapi.repository.UsuarioRepo;

@Service
public class UsuarioService {
      
    
    @Autowired
    private UsuarioRepo userRepository;

    @Autowired
    private ServicioRepo servicioRepository;
    
    //Read
    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    public Usuario getUserById(Integer usuarioId) {
        return userRepository.findById(usuarioId).orElse(null);
    }

    
    // //Create
    public Usuario createUser(Usuario user) {
        return userRepository.save(user);

       
    }
    // public Usuario saveUser(Usuario usuario) {
        //     return userRepository.save(usuario);
        // }


        
    //Update
    public Usuario updateUser(Integer id, Usuario usuarioDetails) {
        Usuario usuario = userRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setId_usuario(usuarioDetails.getId_usuario());
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setApellidos(usuarioDetails.getApellidos());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setContrasena(usuarioDetails.getContrasena());
            usuario.setDireccion(usuarioDetails.getDireccion());
            usuario.setDocumento_identidad(usuarioDetails.getDocumento_identidad());
            usuario.setCelular(usuarioDetails.getCelular());
            usuario.setRol(usuarioDetails.getRol());
            return userRepository.save(usuario);
        }
        return null;
    }

    //Delete
    public boolean deleteUser(Integer id) {
        Usuario usuario = userRepository.findById(id).orElse(null);
        if (usuario != null) {
            userRepository.delete(usuario);
            return true;
        }
        return false;
    }
}
