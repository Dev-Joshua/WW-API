package com.apirest.wwapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
// @CrossOrigin(origins = "http://localhost:4200") // Permitir solicitudes de mi frontend Angular
public class ClienteController {

    @GetMapping("/dashboard")
    public String clienteDashboard() {
        return "Bienvenido al panel de Cliente";
    }
    
    /*
    @GetMapping("/data")
    public ResponseEntity<List<DatosCliente>> obtenerDatosClientes() {
        // Retorna una lista de datos específicos del cliente
        List<DatosCliente> datos = new ArrayList<>();
        // Código para obtener los datos del cliente
        return ResponseEntity.ok(datos);
    }
    */
}
