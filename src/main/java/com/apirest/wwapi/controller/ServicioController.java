package com.apirest.wwapi.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Calificacion;
import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.ServicioService;
import com.apirest.wwapi.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/wwdemo/servicios")
public class ServicioController {

    @Autowired
    private ServicioService serviceService;

    @Autowired
    private UsuarioService usuarioService;
    
    // Metodos para API REST para realizar un CRUD
    @GetMapping
    public List<Servicio> getAllData() {
        return serviceService.getAll();
    }

    @GetMapping("/{id}")
    public Servicio getServiceById(@PathVariable Integer id) {
        return serviceService.findById(id);
    }

    @GetMapping("/{servicioId}/prestadores")
    public ResponseEntity<List<Usuario>> obtenerPrestadoresPorServicio(@PathVariable Integer servicioId) {
        
        Servicio servicio = serviceService.findById(servicioId);
        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Usuario> prestadores = new ArrayList<>(servicio.getUsuarios());
        return ResponseEntity.ok(prestadores);
    }

    @GetMapping("/{servicioId}/prestadores/filtrar")
    public ResponseEntity<List<Usuario>> filtrarPrestadoresPorServicio(
            @PathVariable Integer servicioId,
            @RequestParam(defaultValue = "calificacion") String ordenarPor) {

        Servicio servicio = serviceService.findById(servicioId);

        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Usuario> prestadores = new ArrayList<>(servicio.getUsuarios());

        // Filtrar según el parámetro ordenarPor
        switch (ordenarPor.toLowerCase()) {
            case "calificacion":
                prestadores.sort(Comparator.comparingDouble(this::obtenerCalificacionPromedio).reversed());
                break;
            case "comentarios":
                prestadores.sort(Comparator.comparingInt(this::obtenerNumeroDeComentarios).reversed());
                break;
            case "ubicacion":
                // Implementar lógica para ubicacion
                break;
        }

        return ResponseEntity.ok(prestadores);
    }

    private double obtenerCalificacionPromedio(Usuario usuario) {
        List<Calificacion> calificaciones = usuario.getCalificaciones();
        return calificaciones.stream()
                .mapToInt(Calificacion::getCalificacion)
                .average()
                .orElse(0.0);
    }

    private int obtenerNumeroDeComentarios(Usuario usuario) {
        return usuario.getCalificaciones().size();
    }

    @PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return serviceService.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void deleteServicio(@PathVariable Integer id) {
        serviceService.deleteById(id);
    }

    
}
