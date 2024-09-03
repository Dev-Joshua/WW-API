package com.apirest.wwapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Calificacion;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.CalificacionRepo;

@Service
public class CalificacionService {
    @Autowired
    private CalificacionRepo calificacionRepo;

    @Autowired
    @Lazy
    private UsuarioService userService;

    public List<Calificacion> getCalificacionByUsuario(Integer usuarioId) {

        Usuario usuario = userService.getUserById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return calificacionRepo.findByUsuario(usuario);
    }

    public double getCalificacionPromedio(Integer usuario) {
        List<Calificacion> calificaciones = calificacionRepo.findByUsuarioId(usuario);
        if (calificaciones.isEmpty()) return 0;

        double suma = 0;
        for (Calificacion calificacion : calificaciones) {
            suma += calificacion.getCalificacion();
        }
        return suma / calificaciones.size();
    }

    public List<Calificacion> getCalificacionBySolicitud(Solicitud solicitud) {
        return calificacionRepo.findBySolicitud(solicitud);
    }

    
    public Calificacion saveCalificacion(Calificacion calificacion) {
        return calificacionRepo.save(calificacion);
    }
}
