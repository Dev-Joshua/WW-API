package com.apirest.wwapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Solicitud.Estado;
import com.apirest.wwapi.model.SolicitudDTO;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.repository.PagoRepo;
import com.apirest.wwapi.repository.SolicitudRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SolicitudService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudService.class);

    @Autowired
    private SolicitudRepo requestRepository;

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PagoRepo payrepRepository;

    @Autowired
    private UsuarioService userService;

    public List<SolicitudDTO> findByUsuarioDTO(Integer usuarioId) {
        List<Solicitud> solicitudes = requestRepository.findByUsuarioId(usuarioId);
        return solicitudes.stream().map(solicitud -> new SolicitudDTO(
            solicitud.getId_solicitud(),
            solicitud.getCliente().getNombre(),
            solicitud.getPrestador().getNombre(),
            solicitud.getServicio().getNombre_servicio(),
            solicitud.getMascota().getNombre(),
            solicitud.getFecha_solicitud(),
            solicitud.getEstado().name()
        )).collect(Collectors.toList());
    }

    
    public List<SolicitudDTO> findByPrestadorDTO(Integer prestadorId) {
        List<Solicitud> solicitudes = requestRepository.findByPrestadorId(prestadorId);
        return solicitudes.stream().map(solicitud -> new SolicitudDTO(
            solicitud.getId_solicitud(),
            solicitud.getCliente().getNombre(),
            solicitud.getPrestador().getNombre(),
            solicitud.getServicio().getNombre_servicio(),
            solicitud.getMascota().getNombre(),
            solicitud.getFecha_solicitud(),
            solicitud.getEstado().name()
        )).collect(Collectors.toList());
    }

    public List<Solicitud> getAll() {
        // logger.info("Obteniendo todas las solicitudes");
        return requestRepository.findAll();
    }

    public Solicitud findByIdRequest(Integer id) {
        // logger.info("Buscando solicitud por ID: {}", id);
        return (Solicitud) requestRepository.findByUsuarioId(id);
    }

    public List<Solicitud> findByUsuario(Integer usuarioId) {
        //  logger.info("Buscando solicitudes por usuario: {}", usuario.getId_usuario());
        return requestRepository.findByUsuarioId(usuarioId);
    }

     // Obtener las solicitudes que están en estado PENDIENTE para un prestador
    public List<Solicitud> getSolicitudesPendientes(Integer prestadorId) {
        Usuario prestador = new Usuario();
        prestador.setId_usuario(prestadorId);
        return requestRepository.findByPrestadorAndEstado(prestador, Solicitud.Estado.PENDIENTE);
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

     public Solicitud cambiarEstado(Integer id, Estado nuevoEstado) {
        Solicitud solicitud = requestRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        solicitud.setEstado(nuevoEstado);
        return requestRepository.save(solicitud);
    }

    public void deleteById(Integer id) {
        requestRepository.deleteById(id);
    }

  
}
