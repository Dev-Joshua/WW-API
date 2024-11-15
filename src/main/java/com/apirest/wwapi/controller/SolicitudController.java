package com.apirest.wwapi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Mascota;
import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.model.Solicitud.Estado;
import com.apirest.wwapi.model.SolicitudDTO;
import com.apirest.wwapi.service.MascotaService;
// import com.apirest.wwapi.service.NotificacionService;
import com.apirest.wwapi.service.ServicioService;
import com.apirest.wwapi.service.SolicitudService;
import com.apirest.wwapi.service.UsuarioService;

// Controlador para las solicitudes 
@RestController
@RequestMapping("/api/v1/wwdemo/solicitudes")
public class SolicitudController {


    private static final Logger logger = LoggerFactory.getLogger(SolicitudController.class);
    private static final LocalDateTime LocalDateTime = null;

    @Autowired
    private SolicitudService requestService;

    @Autowired
    private UsuarioService userService;

    @Autowired
    private MascotaService petService;

    @Autowired
    private ServicioService servicioService;

 
    // Metodos para API REST
    @GetMapping
    public List<Solicitud> getAllRequests() {
        return requestService.getAll();
    }

    @GetMapping("/{id}")
    public Solicitud getRequestById(@PathVariable Integer id) {
        return requestService.findByIdRequest(id);
    }

 

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Solicitud> createQuery(
            @PathVariable Integer usuarioId,
            @RequestBody Map<String, Object> requestPayLoad) {
        
        //Buscar el usuario 'cliente'
        Usuario cliente = userService.getUserById(usuarioId);
        if (cliente == null) {
            // logger.info("Cliente no encontrado: {}", usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //Buscar la mascota
        Integer mascotaId = ((Number) requestPayLoad.get("mascota_id")).intValue();
        Mascota mascota = petService.getPetById(mascotaId);
        if (mascota == null) {
            // logger.info("Mascota no encontrada: {}", mascotaId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //Buscar el servicio solicitado
        Integer servicioId = ((Number) requestPayLoad.get("servicio_id")).intValue();
        Servicio servicio = servicioService.findById(servicioId);
        if (servicio == null) {
            // logger.info("Servicio no encontrado: {}", usuarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //Seleccionar al usuario 'prestador' del servicio
        Integer prestadorId = ((Number) requestPayLoad.get("prestador_id")).intValue();
        Usuario prestador = userService.getUserById(prestadorId);
        Set<Usuario> prestadores = servicio.getUsuarios();
        if (prestadores == null || !prestadores.contains(prestador)) {
            logger.error("Prestador no válido: {}", prestadorId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }



        //Crear solicitud
        Solicitud request = new Solicitud();
        request.setFecha_solicitud(java.time.LocalDateTime.now());
        request.setServicio(servicio);
        request.setMascota(mascota);
        // request.setEstado(Estado.fromString(requestPayLoad.get("estado").toString()));
        request.setCliente(cliente);
        request.setPrestador(prestador);
      
        //Crear pago
        Pago pay = new Pago();
        pay.setMonto(servicio.getPrecio());
        pay.setFechaPago(java.time.LocalDateTime.now());

        // Establecer la fecha de la solicitud (fecha que elige el usuario)
        String fecha = (String) requestPayLoad.get("fecha_solicitud");
        LocalDateTime fechaSolicitud = LocalDateTime.parse(fecha);
        request.setFecha_solicitud(fechaSolicitud);

        request.setPago(pay);

        request.setEstado(Solicitud.Estado.PENDIENTE);

        Solicitud newRequest = requestService.createRequest(request);

     

        return ResponseEntity.status(HttpStatus.CREATED).body(newRequest);
        
    }
    
   

    // Obtener solicitudes por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SolicitudDTO>> obtenerSolicitudesPorUsuario(@PathVariable Integer usuarioId) {
       List<SolicitudDTO> solicitudes = requestService.findByUsuarioDTO(usuarioId);
        if (solicitudes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(solicitudes);
    }

     // Obtener solicitudes por usuario
    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<SolicitudDTO>> obtenerSolicitudesPorPrestador(@PathVariable Integer prestadorId) {
       List<SolicitudDTO> solicitudes = requestService.findByPrestadorDTO(prestadorId);
        if (solicitudes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(solicitudes);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Solicitud> cambiarEstado(@PathVariable Integer id, @RequestBody String nuevoEstado) {
        try {
            Estado estado = Estado.fromString(nuevoEstado);
            Solicitud solicitudActualizada = requestService.cambiarEstado(id, estado);
            return ResponseEntity.ok(solicitudActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSolicitud(@PathVariable Integer id) {
        requestService.deleteById(id);
    }
}
