package com.apirest.wwapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u JOIN u.servicios s WHERE s.id_servicio = :servicioId")
    List<Usuario> findByServicioId(@Param("servicioId") Integer servicioId);

    // Buscar usuarioi por email
    Optional<Usuario> findByEmail(String email);

     // Filtrar usuarios por ciudad, servicio y nombre
    @Query("SELECT u FROM Usuario u JOIN u.servicios s WHERE " +
           "(:ciudad IS NULL OR u.ciudad = :ciudad) " +
           "AND (:servicioId IS NULL OR s.id_servicio = :servicioId) " +
           "AND (:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Usuario> filtrarPrestadores(
        @Param("ciudad") String ciudad,
        @Param("servicioId") Integer servicioId,
        @Param("nombre") String nombre
    );

    
}


