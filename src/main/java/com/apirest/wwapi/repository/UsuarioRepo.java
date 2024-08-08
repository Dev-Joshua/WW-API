package com.apirest.wwapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
   
}


