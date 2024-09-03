package com.apirest.wwapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Usuario;

@Repository
public interface PagoRepo extends JpaRepository<Pago, Integer>{

    List<Pago> findByPrestador(Usuario prestador);

    @Query("SELECT SUM(p.monto) FROM Pago p WHERE p.prestador = :prestador")
    Double findTotalPagosByPrestador(@Param("prestador") Usuario prestador);

}

