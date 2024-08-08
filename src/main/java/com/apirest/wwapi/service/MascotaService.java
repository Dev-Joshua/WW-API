package com.apirest.wwapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.wwapi.model.Mascota;
import com.apirest.wwapi.repository.MascotaRepo;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepo petRepository;

    //Obtener mascotas
    public List<Mascota> getAllPets() {
        return petRepository.findAll();
    }

    //Obtener mascota por ID
    public Mascota getPetById(Integer id) {
        return petRepository.findById(id).orElse(null);
    }

    //Crear una nueva mascota
    public Mascota createPet(Mascota mascota) {
        return petRepository.save(mascota);
    }

    //Actualizar una mascota existente
    public Mascota updatePet(Integer id, Mascota mascotaDetails) {
        Mascota pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            pet.setTipo_mascota(mascotaDetails.getTipo_mascota());
            pet.setNombre(mascotaDetails.getNombre());
            pet.setEdad(mascotaDetails.getEdad());
            pet.setRaza(mascotaDetails.getRaza());
            pet.setPeso(mascotaDetails.getPeso());
            pet.setTamano(mascotaDetails.getTamano());
            pet.setSexo(mascotaDetails.getSexo());
            pet.setEsterilizado(mascotaDetails.getEsterilizado());
            pet.setDescripcion_mascota(mascotaDetails.getDescripcion_mascota());
            pet.setInfo_cuidado(mascotaDetails.getInfo_cuidado());
            pet.setFoto_mascota(mascotaDetails.getFoto_mascota());
            // mascota.setUsuario(mascotaDetails.getUsuario());

            return petRepository.save(pet);
        }
        return null;
    }

    //Eliminar una mascota
    public boolean deletePet(Integer id) {
        Mascota pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            petRepository.delete(pet);
            return true;
        }
        return false;
    }
}

