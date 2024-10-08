package com.apirest.wwapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Calificacion;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.CalificacionService;

@RestController
@RequestMapping("/api/v1/wwdemo/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;


    //Generar una calificacion
    @PostMapping
    public ResponseEntity<Calificacion> crearCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion nuevaCalificacion = calificacionService.saveCalificacion(calificacion);
        return new ResponseEntity<>(nuevaCalificacion, HttpStatus.CREATED);
    }

    //Ver calificacion del usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Calificacion>> obtenerCalificacionesPorUsuario(@PathVariable Integer usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(usuarioId);
        List<Calificacion> calificaciones = calificacionService.getCalificacionByUsuario(usuarioId);
        return ResponseEntity.ok(calificaciones);
    }

    //Ver calificacion con el id de solicitud
    @GetMapping("/solicitud/{solicitudId}")
    public ResponseEntity<List<Calificacion>> obtenerCalificacionesPorSolicitud(@PathVariable Integer solicitudId) {
        Solicitud solicitud = new Solicitud();
        solicitud.setId_solicitud(solicitudId);
        List<Calificacion> calificaciones = calificacionService.getCalificacionBySolicitud(solicitud);
        return ResponseEntity.ok(calificaciones);
    }
}
