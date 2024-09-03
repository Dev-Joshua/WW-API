package com.apirest.wwapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Pago;

@Repository
public interface PagoRepo extends JpaRepository<Pago, Integer>{

}

