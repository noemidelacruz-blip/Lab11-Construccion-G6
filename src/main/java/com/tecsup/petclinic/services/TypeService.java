package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;

import java.util.List;

public interface TypeService {

    TypeDTO create(TypeDTO typeDTO);

    TypeDTO update(TypeDTO typeDTO);

    void delete(Integer id) throws TypeNotFoundException;

    TypeDTO findById(Integer id) throws TypeNotFoundException;

    List<TypeDTO> findAll();

    // Reporte de cantidad de mascotas por tipo (solo tipos activos)
    List<TypePetCountDTO> getPetCountByType();
}