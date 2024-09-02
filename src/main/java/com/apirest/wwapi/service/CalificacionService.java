package com.apirest.wwapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Calificacion;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.CalificacionRepo;

@Service
public class CalificacionService {
    @Autowired
     private CalificacionRepo calificacionRepo;

    public Calificacion saveCalificacion(Calificacion calificacion) {
        return calificacionRepo.save(calificacion);
    }

    public List<Calificacion> findCalificacionesByUsuario(Usuario usuario) {
        return calificacionRepo.findByUsuario(usuario);
    }

    public List<Calificacion> findCalificacionesBySolicitud(Solicitud solicitud) {
        return calificacionRepo.findBySolicitud(solicitud);
    }
}
