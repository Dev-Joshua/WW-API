package com.apirest.wwapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Solicitud;

@Repository
public interface SolicitudRepo extends JpaRepository<Solicitud, Integer>{

}