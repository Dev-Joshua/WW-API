package com.apirest.wwapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.PagoRepo;
import com.apirest.wwapi.repository.SolicitudRepo;

import jakarta.transaction.Transactional;

@Service
public class SolicitudService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudService.class);

    @Autowired
    private SolicitudRepo requestRepository;

    @Autowired
    private PagoRepo payrepRepository;

    public List<Solicitud> getAll() {
        // logger.info("Obteniendo todas las solicitudes");
        return requestRepository.findAll();
    }

    public Solicitud findByIdRequest(Integer id) {
        // logger.info("Buscando solicitud por ID: {}", id);
        return requestRepository.findById(id).orElse(null);
    }

    public List<Solicitud> findByUsuario(Usuario usuario) {
        //  logger.info("Buscando solicitudes por usuario: {}", usuario.getId_usuario());
        return requestRepository.findByClienteOrPrestador(usuario, usuario);
    }

    @Transactional
    public Solicitud createRequest(Solicitud request) {
        Pago pay = request.getPago();
        if (pay != null) {
            pay.setSolicitud(request);  // Establece la relación bidireccional
            payrepRepository.save(pay);
        }
        // logger.info("Creando solicitud: {}", request);
        return requestRepository.save(request);
    }

    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }
}
