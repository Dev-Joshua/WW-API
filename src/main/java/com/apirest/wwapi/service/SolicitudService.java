package com.apirest.wwapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.repository.PagoRepo;
import com.apirest.wwapi.repository.SolicitudRepo;

import jakarta.transaction.Transactional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepo requestRepository;

     @Autowired
    private PagoRepo payrepRepository;

    public List<Solicitud> getAll() {
        return requestRepository.findAll();
    }

    public Solicitud findByIdRequest(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Transactional
    public Solicitud createRequest(Solicitud request) {
        Pago pay = request.getPago();
        if (pay != null) {
            pay.setSolicitud(request);  // Establece la relación bidireccional
            payrepRepository.save(pay);
        }
        return requestRepository.save(request);
    }

    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }
}
