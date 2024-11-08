package com.apirest.wwapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.apirest.wwapi.model.Usuario;
import com.apirest.wwapi.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService userService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testGetAllUsers() {
        List<Usuario> usuarios = Collections.singletonList(new Usuario());
        when(userService.getAllUsers()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = (ResponseEntity<List<Usuario>>) usuarioController.getAllData();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() == 1; 
    }

    @Test
    void testCreateUser() {
        Usuario usuario = new Usuario();
        when(userService.createUser(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.crearUsuario(usuario);
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() != null;
    }

    // Agrega más pruebas para los métodos restantes...
}

