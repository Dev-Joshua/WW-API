package com.apirest.wwapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.PagoRepo;

@Service
public class PagoService {

    @Autowired
    private PagoRepo payRepository;

    public List<Pago> getAllPays() {
        return payRepository.findAll();
    }

    public Pago getPayById(Integer id) {
        return payRepository.findById(id).orElse(null);
    }

    public Pago savePay(Pago payment) {
        return payRepository.save(payment);
    }

    public void deletePago(Integer id) {
        payRepository.deleteById(id);
    }

    public List<Pago> findPagosByPrestador(Usuario prestador) {
        return payRepository.findByPrestador(prestador);
    }

    public Double calcularTotalPagosPorPrestador(Usuario prestador) {
        return payRepository.findTotalPagosByPrestador(prestador);
    }
}
