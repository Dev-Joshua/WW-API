package com.apirest.wwapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Mascota;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.MascotaService;
import com.apirest.wwapi.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

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
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody Usuario usuarioDetails) {
       Usuario usuario = userService.getUserById(id);
       if (usuario == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setApellidos(usuarioDetails.getApellidos());
        usuario.setDocumento_identidad(usuarioDetails.getDocumento_identidad());
        usuario.setDireccion(usuarioDetails.getDireccion());
        usuario.setCelular(usuarioDetails.getCelular());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setContrasena(usuarioDetails.getContrasena());
        usuario.setRol(usuarioDetails.getRol());

        userService.createUser(usuario);

        return ResponseEntity.ok("Usuario actualizado exitosamente");
    }
    

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id) {
            Usuario usuario = userService.getUserById(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
                }
            
                userService.deleteUser(id);
                return ResponseEntity.ok("Usuario eliminado exitosamente");
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

}
