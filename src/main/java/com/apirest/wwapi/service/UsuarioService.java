package com.apirest.wwapi.service;


import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.ServicioRepo;
import com.apirest.wwapi.repository.UsuarioRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
      
    private static final Logger logger = LoggerFactory.getLogger(SolicitudService.class);

    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    private UsuarioRepo userRepository;

    @Autowired
    private ServicioRepo servicioRepository;

    @Autowired
    private CalificacionService calificacionService;
    
    //Read
    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    public Usuario getUserById(Integer usuarioId) {
        return userRepository.findById(usuarioId).orElse(null);
    }

    // Método para obtener el usuario por su email (extraído del token JWT)
    public Usuario findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
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

        // usuarios proveedores
        public List<Usuario> getPrestadoresPorServicio(Integer servicioId) {
        List<Usuario> usuarios = userRepository.findByServicioId(servicioId);
        // Puedes ordenar la lista de usuarios por calificación promedio aquí
        usuarios.sort((u1, u2) -> {
            double calificacionPromedio1 = calificacionService.getCalificacionPromedio(u1.getId_usuario());
            double calificacionPromedio2 = calificacionService.getCalificacionPromedio(u2.getId_usuario());
            return Double.compare(calificacionPromedio2, calificacionPromedio1); // Orden descendente
        });
        return usuarios;
    }

     
        
    //Update
    @Transactional
    public Usuario updateUser(Integer id, Usuario usuarioDetails) {
        Usuario usuario = userRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setId_usuario(usuarioDetails.getId_usuario());
            usuario.setFoto_usuario(usuarioDetails.getFoto_usuario());
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setApellidos(usuarioDetails.getApellidos());
            usuario.setEmail(usuarioDetails.getEmail());

            // Codificar la nueva contraseña solo si se proporciona
        if (usuarioDetails.getContrasena() != null && !usuarioDetails.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDetails.getContrasena()));
        }
            
            usuario.setCiudad(usuarioDetails.getCiudad());
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
