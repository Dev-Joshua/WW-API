package com.apirest.wwapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Mascota;

@Repository
public interface MascotaRepo extends JpaRepository<Mascota, Integer>{

}
