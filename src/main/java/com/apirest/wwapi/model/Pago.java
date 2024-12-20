package com.apirest.wwapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// Entidad Pago
@Entity
@Table(name = "pagos")
public class Pago {
    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer id_pago;

    @Column(name = "monto")
    private Float monto;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    //Relaciones
    @OneToOne
    @JoinColumn(name = "solicitud_id")
    @JsonBackReference("pago-solicitud")
    private Solicitud solicitud;

    // Relación con Usuario (prestador)
    @ManyToOne
    @JoinColumn(name = "prestador_id", referencedColumnName = "id_usuario")
    private Usuario prestador;


    //Getters & Setters
    public Integer getId_pago() {
        return id_pago;
    }

    public void setId_pago(Integer id_pago) {
        this.id_pago = id_pago;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }




    
    
}
