package com.apirest.wwapi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Mascota;
import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Servicio;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.MascotaService;
import com.apirest.wwapi.service.ServicioService;
import com.apirest.wwapi.service.SolicitudService;
import com.apirest.wwapi.service.UsuarioService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

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
    public Solicitud getRequestById(@PathVariable Long id) {
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

        
        
        //Buscar el usuario 'cliente'
        Usuario cliente = userService.getUserById(usuarioId);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //Buscar la mascota
        Integer mascotaId = ((Number) requestPayLoad.get("mascota_id")).intValue();
        Mascota mascota = petService.getPetById(mascotaId);
        if (mascota == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //Buscar el servicio solicitado
        Integer servicioId = ((Number) requestPayLoad.get("servicio_id")).intValue();
        Servicio servicio = servicioService.findById(servicioId);
        if (servicio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        //Seleccionar al usuario 'prestador' del servicio
        Integer prestadorId = ((Number) requestPayLoad.get("prestador_id")).intValue();
        Usuario prestador = userService.getUserById(prestadorId);
        if (prestador == null || !servicio.getUsuarios().contains(prestador)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }



        //Crear solicitud
        Solicitud request = new Solicitud();
        request.setFecha_solicitud(LocalDateTime.now());
        request.setServicio(servicio);
        request.setMascota(mascota);
        request.setEstado((String) requestPayLoad.get("estado"));
        request.setCliente(cliente);
        request.setPrestador(prestador);
      
        // Map<String, Object> paymentLoad = (Map<String, Object>) requestPayLoad.get("pago");
        // Pago pay = new Pago();
        // pay.setMonto(((Number) paymentLoad.get("monto")).floatValue());
        // pay.setFechaPago(LocalDateTime.now());

        Pago pay = new Pago();
        pay.setMonto(servicio.getPrecio());
        pay.setFechaPago(LocalDateTime.now());

        request.setPago(pay);

        Solicitud newRequest = requestService.createRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(newRequest);
    }
    
    public Solicitud createRequest(@RequestBody Solicitud solicitud) {
        return requestService.createRequest(solicitud);
    }

    @DeleteMapping("/{id}")
    public void deleteSolicitud(@PathVariable Long id) {
        requestService.deleteById(id);
    }
}
