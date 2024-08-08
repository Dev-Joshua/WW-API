package com.apirest.wwapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Servicio;

@Repository
public interface ServicioRepo extends JpaRepository<Servicio, Integer> {
   
}

