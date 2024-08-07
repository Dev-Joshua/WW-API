package com.apirest.wwapi.model;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Mascota {

@Entity
@Table(name = "mascotas")
public class Mascota {

    // Variables de la clase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer id_mascota;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mascota")
    private Tipo tipo_mascota;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "edad")
    private String edad;

    @Column(name = "raza")
    private String raza;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "tamano")
    private String tamano;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sex sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "esterilizado")
    private Esterilizado esterilizado;

    @Column(name = "descripcion_mascota")
    private String descripcion_mascota;

    @Column(name = "info_cuidado")
    private String info_cuidado;

    @Column(name = "foto_mascota")
    private String foto_mascota;
    
    // Relaciones
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference("usuario-mascota")
    private Usuario usuario;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("mascota-solicitud")
    private Set<Solicitud> solicitudes = new HashSet<>();


    public enum Tipo {
        Canino, Felino
    }

    public enum Sex {
        Masculino, Femenino
    }

    public enum Esterilizado {
        Si, No
    }
 }
}
