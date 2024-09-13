package com.apirest.wwapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.wwapi.model.Pago;
import com.apirest.wwapi.model.Solicitud;
import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.PagoService;
import com.apirest.wwapi.service.SolicitudService;
import com.apirest.wwapi.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/wwdemo/prestador")
// @CrossOrigin(origins = "http://localhost:4200") 
public class PrestadorController {
    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private UsuarioService usuarioService;

    // Obtener solicitudes recibidas por el prestador
    @GetMapping("/{prestadorId}/solicitudes")
    public ResponseEntity<List<Solicitud>> obtenerSolicitudesPorPrestador(@PathVariable Integer prestadorId) {
        Usuario prestador = usuarioService.getUserById(prestadorId);

        if (prestador == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Solicitud> solicitudes = solicitudService.findByUsuario(prestador);
        return ResponseEntity.ok(solicitudes);
    }

    // Obtener pagos recibidos por el prestador
    @GetMapping("/{prestadorId}/pagos")
    public ResponseEntity<List<Pago>> obtenerPagosPorPrestador(@PathVariable Integer prestadorId) {
        Usuario prestador = usuarioService.getUserById(prestadorId);

        if (prestador == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Pago> pagos = pagoService.findPagosByPrestador(prestador);
        return ResponseEntity.ok(pagos);
    }

    // Obtener el monto total recibido por el prestador
    @GetMapping("/{prestadorId}/total-pagos")
    public ResponseEntity<Double> obtenerTotalPagosPorPrestador(@PathVariable Integer prestadorId) {
        Usuario prestador = usuarioService.getUserById(prestadorId);

        if (prestador == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Double totalPagos = pagoService.calcularTotalPagosPorPrestador(prestador);
        return ResponseEntity.ok(totalPagos);
    }

    @GetMapping("/dashboard")
    public String prestadorDashboard() {
        return "Bienvenido al panel de Prestador";
    }

    /*
    @GetMapping("/data")
    public ResponseEntity<List<DatosPrestador>> obtenerDatosPrestadores() {
        // Retorna una lista de datos específicos del prestador
        List<DatosPrestador> datos = new ArrayList<>();
        // Código para obtener los datos del prestador
        return ResponseEntity.ok(datos);
    }
    */
}