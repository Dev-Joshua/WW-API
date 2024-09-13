package com.apirest.wwapi.auth;

import com.apirest.wwapi.model.Usuario.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String nombre;
    String apellidos;
    String documento_identidad;
    String direccion;
    String celular;
    String email;
    String contrasena;
    Role rol;
    
}
