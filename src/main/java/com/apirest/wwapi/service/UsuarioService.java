package com.apirest.wwapi.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.core.GrantedAuthority;
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

    @Autowired
    private CalificacionService calificacionService;
    
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

     // Implementación del método loadUserByUsername de UserDetailsService
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     Usuario usuario = userRepository.findByEmail(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + username));
    //     return new org.springframework.security.core.userdetails.User(
    //             usuario.getEmail(), 
    //             usuario.getContrasena(), 
    //             getAuthorities(usuario)
    //     );
    // }

    // private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
    //     return Arrays.stream(usuario.getRol().name().split(","))
    //             .map(SimpleGrantedAuthority::new)
    //             .collect(Collectors.toList());
    // }

    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     Usuario usuario = userRepository.findByEmail(email)
    //             .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
    //     return new org.springframework.security.core.userdetails.User(
    //             usuario.getEmail(),
    //             usuario.getContrasena(),
    //             Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
    //     );
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
