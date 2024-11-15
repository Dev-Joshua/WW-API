package com.apirest.wwapi.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Mascota;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.MascotaService;
import com.apirest.wwapi.service.SolicitudService;
import com.apirest.wwapi.service.UsuarioService;

import lombok.RequiredArgsConstructor;

// Controlador para las acciones del usuario
@RestController
@RequestMapping("/api/v1/wwdemo/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private static final Logger logger = LoggerFactory.getLogger(SolicitudService.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService userService;
    
    @Autowired
    private MascotaService petService;
    
    //Metodos para API REST
    @GetMapping
    public List<Usuario> getAllData() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public Usuario getUseroById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }


    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
     public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = userService.createUser(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
        Usuario usuario = userService.getUserById(id);
        if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Usuario no encontrado"));
        }

        if (usuarioDetails.getContrasena() != null && !usuarioDetails.getContrasena().isEmpty()) {
            logger.info("Actualizando contraseña para el usuario ID: {}", id);
            usuario.setContrasena(passwordEncoder.encode(usuarioDetails.getContrasena()));
        } else {
            logger.info("No se ha proporcionado una nueva contraseña, manteniendo la contraseña actual para el usuario ID: {}", id);
            //Mantener la contraseña sin cambios
            usuario.setContrasena(usuario.getContrasena());
        }

        
        usuario.setFoto_usuario(usuarioDetails.getFoto_usuario());
        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setApellidos(usuarioDetails.getApellidos());
        usuario.setDocumento_identidad(usuarioDetails.getDocumento_identidad());
        usuario.setCiudad(usuarioDetails.getCiudad());
        usuario.setDireccion(usuarioDetails.getDireccion());
        usuario.setCelular(usuarioDetails.getCelular());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setContrasena(usuarioDetails.getContrasena());
        usuario.setRol(usuarioDetails.getRol());

        userService.updateUser(id, usuario);

        return ResponseEntity.ok(Collections.singletonMap("message", "Usuario actualizado con exito"));
    }
    

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id) {
            Usuario usuario = userService.getUserById(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario no encontrado");
            }
            
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
    }
            
            
    @GetMapping("/{id}/mascotas")
    public ResponseEntity<List<Mascota>> obtenerMascotasPorUsuario(@PathVariable Integer id) {
        Usuario usuario = userService.getUserById(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(usuario.getMascotas());
    }

    @PostMapping("/{id}/mascotas")
    public ResponseEntity<Mascota> crearMascota(@PathVariable Integer id, @RequestBody Mascota mascota) {
        Usuario usuario = userService.getUserById(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        mascota.setUsuario(usuario);
        Mascota nuevaMascota = petService.createPet(mascota);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMascota);
    }
    
    @GetMapping("/filtrar")
    public ResponseEntity<List<Usuario>> filtrarPrestadores(
        @RequestParam(required = false) String ciudad,
        @RequestParam(required = false) Integer servicioId,
        @RequestParam(required = false) String nombre
    ) {
        List<Usuario> prestadores = userService.filtrarPrestadores(ciudad, servicioId, nombre);
        return ResponseEntity.ok(prestadores);
    }
}
