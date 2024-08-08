package com.apirest.wwapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.repository.ServicioRepo;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepo serviceRepository;

    public List<Servicio> getAll() {
        return serviceRepository.findAll();
    }

    public Servicio findById(Integer id) {
        Optional<Servicio> servicio = serviceRepository.findById(id);
        return servicio.orElse(null);
    }

    public Servicio save(Servicio servicio) {
        return serviceRepository.save(servicio);
    }

    public void deleteById(Integer id) {
        serviceRepository.deleteById(id);
    }
}
