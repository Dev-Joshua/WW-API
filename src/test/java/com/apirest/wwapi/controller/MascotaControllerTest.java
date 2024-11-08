package com.apirest.wwapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.apirest.wwapi.model.Mascota;
import com.apirest.wwapi.service.MascotaService;
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

class MascotaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MascotaService petService;

    @InjectMocks
    private MascotaController mascotaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
    }

    @Test
    void testGetAllPets() {
        List<Mascota> mascotas = Collections.singletonList(new Mascota());
        when(petService.getAllPets()).thenReturn(mascotas);

        ResponseEntity<List<Mascota>> response = (ResponseEntity<List<Mascota>>) mascotaController.getAllData();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
    }

   void testCreatePet() {
    Mascota mascota = new Mascota();
    when(petService.createPet(any(Mascota.class))).thenReturn(mascota);
   }
}
