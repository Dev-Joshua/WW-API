package com.apirest.wwapi.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.ServicioService;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService serviceService;
    
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

    @PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return serviceService.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void deleteServicio(@PathVariable Integer id) {
        serviceService.deleteById(id);
    }
}
