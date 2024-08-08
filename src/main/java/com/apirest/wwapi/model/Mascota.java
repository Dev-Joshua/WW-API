package com.apirest.wwapi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


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


    //Getters & Setters
    public Integer getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(Integer id_mascota) {
        this.id_mascota = id_mascota;
    }

    public Tipo getTipo_mascota() {
        return tipo_mascota;
    }

    public void setTipo_mascota(Tipo tipo_mascota) {
        this.tipo_mascota = tipo_mascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public Sex getSexo() {
        return sexo;
    }

    public void setSexo(Sex sexo) {
        this.sexo = sexo;
    }

    public Esterilizado getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(Esterilizado esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getDescripcion_mascota() {
        return descripcion_mascota;
    }

    public void setDescripcion_mascota(String descripcion_mascota) {
        this.descripcion_mascota = descripcion_mascota;
    }

    public String getInfo_cuidado() {
        return info_cuidado;
    }

    public void setInfo_cuidado(String info_cuidado) {
        this.info_cuidado = info_cuidado;
    }

    public String getFoto_mascota() {
        return foto_mascota;
    }

    public void setFoto_mascota(String foto_mascota) {
        this.foto_mascota = foto_mascota;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(Set<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }


    
 }

