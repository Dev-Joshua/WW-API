package com.apirest.wwapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.model.Usuario.Role;
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

    
    //Create
    public Usuario createUser(Usuario user) {

        Usuario nuevoUsuario = userRepository.save(user);
        
        switch (user.getRol()) {
            case PASEADOR:
                 asociarUsuarioConServicio(nuevoUsuario, "Caminata");
                break;
            case CUIDADOR:
                 asociarUsuarioConServicio(nuevoUsuario, "Guarderia");
                break;
            case ENTRENADOR:
                 asociarUsuarioConServicio(nuevoUsuario, "Entrenamiento");
                break;
            case CLIENTE:
                break;
        }
        
        return nuevoUsuario;
    }

     private void asociarUsuarioConServicio(Usuario usuario, String nombre_servicio) {
        // Buscar el servicio correspondiente
        Optional<Servicio> servicioOpt = servicioRepository.findByNombre_servicio(nombre_servicio);

        if (servicioOpt.isPresent()) {
            Servicio servicio = servicioOpt.get();
            servicio.getUsuarios().add(usuario);
            usuario.getServicios().add(servicio);

            // Guardar la relación en la tabla usuarios_servicios
            servicioRepository.save(servicio);
        } else {
            throw new RuntimeException("Servicio " + nombre_servicio + " no encontrado");
        }
    }
        
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
