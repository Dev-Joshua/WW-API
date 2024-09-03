package com.apirest.wwapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class Solicitud {
    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer id_solicitud;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fecha_solicitud;

    //Relaciones 
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    @JsonBackReference("servicio-solicitud")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    @JsonBackReference("mascota-solicitud")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference("cliente-solicitud")
    private Usuario cliente;  // Este es el usuario que solicita el servicio

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    @JsonBackReference("prestador-solicitud")
    private Usuario prestador;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    @JsonManagedReference("pago-solicitud")
    private Pago pago;
   
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;


    public enum Estado {
        PENDIENTE("Pendiente"),
        EN_CURSO("En curso"),
        FINALIZADO("Finalizado");

        private final String displayName;

        Estado(String displayName) {
            this.displayName = displayName;
        }

        @JsonValue
        public String getDisplayName() {
            return displayName;
        }
        
        @JsonCreator
        public static Estado fromString(String value) {
        for (Estado estado : Estado.values()) {
            if (estado.displayName.equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + value);
        }
    }
    
    //Getters & Setters
    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public LocalDateTime getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(LocalDateTime fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getPrestador() {
        return prestador;
    }

    public void setPrestador(Usuario prestador) {
        this.prestador = prestador;
    }

    
}
