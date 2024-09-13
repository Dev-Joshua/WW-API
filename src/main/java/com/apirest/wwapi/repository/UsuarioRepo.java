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

    // Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}


