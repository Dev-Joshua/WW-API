package com.apirest.wwapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;

@Repository
public interface SolicitudRepo extends JpaRepository<Solicitud, Integer>{

     @Query("SELECT s FROM Solicitud s " +
           "JOIN FETCH s.servicio " +
           "JOIN FETCH s.mascota " +
           "JOIN FETCH s.cliente " +
           "JOIN FETCH s.prestador " +
           "WHERE s.cliente.id_usuario = :usuarioId")
    List<Solicitud> findByUsuarioId(@Param("usuarioId") Integer id);
    
    List<Solicitud> findByClienteOrPrestador(Usuario cliente, Usuario prestador);

    List<Solicitud> findByPrestadorAndEstado(Usuario prestador, Solicitud.Estado estado);

    @Query("SELECT s FROM Solicitud s " +
           "JOIN FETCH s.servicio " +
           "JOIN FETCH s.mascota " +
           "JOIN FETCH s.cliente " +
           "JOIN FETCH s.prestador " +
           "WHERE s.prestador.id_usuario = :prestadorId")
    List<Solicitud> findByPrestadorId(@Param("prestadorId") Integer id);

    // Método para encontrar una solicitud por ID
    @SuppressWarnings("null")
    Optional<Solicitud> findById(Integer id);
}