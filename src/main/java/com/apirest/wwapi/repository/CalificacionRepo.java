package com.apirest.wwapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Calificacion;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;

@Repository
public interface CalificacionRepo extends JpaRepository<Calificacion, Integer> {
    List<Calificacion> findByUsuario(Usuario usuario);
    List<Calificacion> findBySolicitud(Solicitud solicitud);
}
