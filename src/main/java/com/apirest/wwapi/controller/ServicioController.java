package com.apirest.wwapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.service.ServicioService;

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

    @PostMapping
    public Servicio createServicio(@RequestBody Servicio servicio) {
        return serviceService.save(servicio);
    }

    @DeleteMapping("/{id}")
    public void deleteServicio(@PathVariable Integer id) {
        serviceService.deleteById(id);
    }
}
