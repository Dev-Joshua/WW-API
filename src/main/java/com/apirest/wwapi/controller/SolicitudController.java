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
import com.apirest.wwapi.service.MascotaService;
import com.apirest.wwapi.service.ServicioService;
import com.apirest.wwapi.service.SolicitudService;
import com.apirest.wwapi.service.UsuarioService;

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

    @GetMapping("/{servicioId}/prestadores")
    public ResponseEntity<List<Usuario>> getServiceProvider(@PathVariable Integer servicioId) {
        
        Servicio servicio = servicioService.findById(servicioId);
        
        if (servicio == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Usuario> prestadores = new ArrayList<>(servicio.getUsuarios());
        return ResponseEntity.ok(prestadores);
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Solicitud> createQuery(
            @PathVariable Integer usuarioId,
            @RequestBody Map<String, Object> requestPayLoad) {

        // logger.info("Request payload: {}", requestPayLoad);
        
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
        request.setEstado(Estado.fromString(requestPayLoad.get("estado").toString()));
        request.setCliente(cliente);
        request.setPrestador(prestador);
      
        //Crear pago
        Pago pay = new Pago();
        pay.setMonto(servicio.getPrecio());
        pay.setFechaPago(java.time.LocalDateTime.now());

        request.setPago(pay);

        Solicitud newRequest = requestService.createRequest(request);
        // logger.info("Solicitud creada con éxito: {}", newRequest.getId_solicitud());

        return ResponseEntity.status(HttpStatus.CREATED).body(newRequest);
        
    }
    
    public Solicitud createRequest(@RequestBody Solicitud solicitud) {
        return requestService.createRequest(solicitud);
    }

    // Aceptar o rechazar una solicitud
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Solicitud> cambiarEstadoSolicitud(
            @PathVariable Integer id, 
            @RequestParam Estado estado) {
        Solicitud solicitud = requestService.findByIdRequest(id);

        if (solicitud == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (estado == Estado.EN_CURSO || estado == Estado.PENDIENTE) {
            solicitud.setEstado(estado);
            requestService.createRequest(solicitud);
            return ResponseEntity.ok(solicitud);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Iniciar un servicio
    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<Solicitud> iniciarServicio(@PathVariable Integer id) {
        Solicitud solicitud = requestService.findByIdRequest(id);

        if (solicitud == null || solicitud.getEstado() != Estado.EN_CURSO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        solicitud.setEstado(Estado.EN_CURSO);
        solicitud.setFecha_solicitud(LocalDateTime);
        requestService.createRequest(solicitud);
        return ResponseEntity.ok(solicitud);
    }

    // Finalizar un servicio
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Solicitud> finalizarServicio(@PathVariable Integer id) {
        Solicitud solicitud = requestService.findByIdRequest(id);

        if (solicitud == null || solicitud.getEstado() != Estado.EN_CURSO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        solicitud.setEstado(Estado.FINALIZADO);
        solicitud.setFecha_solicitud(LocalDateTime);
        requestService.createRequest(solicitud);
        return ResponseEntity.ok(solicitud);
    }

    // Obtener solicitudes por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Solicitud>> obtenerSolicitudesPorUsuario(@PathVariable Integer usuarioId) {
        Usuario usuario = userService.getUserById(usuarioId);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Solicitud> solicitudes = requestService.findByUsuario(usuario);
        return ResponseEntity.ok(solicitudes);
    }

    @DeleteMapping("/{id}")
    public void deleteSolicitud(@PathVariable Integer id) {
        requestService.deleteById(id);
    }
}
