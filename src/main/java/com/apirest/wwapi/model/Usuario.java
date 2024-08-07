package com.apirest.wwapi.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
     //Variables de la clase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id_usuario;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "documento_identidad")
    private String documento_identidad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "celular")
    private String celular;

    @Column(name = "email")
    private String email;

    @Column(name = "contrasena")
    private String contrasena;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Role rol;

    // Relaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("usuario-mascota")
    private List<Mascota> mascotas;

    // @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonManagedReference("usuario-solicitud")
    // private Set<Solicitud> solicitudes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "usuarios_servicios",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    @JsonBackReference("usuario-servicio")
    private Set<Servicio> servicios = new HashSet<>();

    public enum Role {
        CLIENTE, ENTRENADOR, PASEADOR, CUIDADOR
    }

}

