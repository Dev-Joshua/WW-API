package com.apirest.wwapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;

@Repository
public interface SolicitudRepo extends JpaRepository<Solicitud, Integer>{
    List<Solicitud> findByClienteOrPrestador(Usuario cliente, Usuario prestador);
}