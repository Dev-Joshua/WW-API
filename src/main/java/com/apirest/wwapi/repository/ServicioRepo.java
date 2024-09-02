package com.apirest.wwapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Servicio;



@Repository
public interface ServicioRepo extends JpaRepository<Servicio, Integer> {

   @Query("SELECT s FROM Servicio s WHERE s.nombre_servicio = :nombre_servicio")
   Optional<Servicio> findByNombre_servicio(@Param("nombre_servicio")String nombre_servicio);
}


