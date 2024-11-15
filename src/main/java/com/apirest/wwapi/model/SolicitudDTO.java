package com.apirest.wwapi.model;

import java.time.LocalDateTime;

// Entidad solciitudDTO para listar datos de los dos usuarios cliente/prestador
public class SolicitudDTO {
    private Integer id_solicitud;
    private LocalDateTime fechaSolicitud;
    private String estado;
    private String clienteNombre;
    private String prestadorNombre;
    private String servicioNombre;
    private String mascotaNombre;

     // Constructor con todos los parámetros
    public SolicitudDTO(Integer id, String clienteNombre, String prestadorNombre, String servicioNombre, String mascotaNombre, LocalDateTime fechaSolicitud, String estado) {
        this.id_solicitud = id;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.clienteNombre = clienteNombre;
        this.prestadorNombre = prestadorNombre;
        this.servicioNombre = servicioNombre;
        this.mascotaNombre = mascotaNombre;
    }
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Integer getId() {
        return id_solicitud;
    }
    public void setId(Integer id) {
        this.id_solicitud = id;
    }
    public String getClienteNombre() {
        return clienteNombre;
    }
    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
    public String getPrestadorNombre() {
        return prestadorNombre;
    }
    public void setPrestadorNombre(String prestadorNombre) {
        this.prestadorNombre = prestadorNombre;
    }
    public String getServicioNombre() {
        return servicioNombre;
    }
    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }
    public String getMascotaNombre() {
        return mascotaNombre;
    }
    public void setMascotaNombre(String mascotaNombre) {
        this.mascotaNombre = mascotaNombre;
    }

}
