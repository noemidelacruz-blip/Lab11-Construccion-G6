package com.tecsup.petclinic.services;

<<<<<<< HEAD
import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.repositories.PetRepository;
=======
import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
>>>>>>> origin/main
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

<<<<<<< HEAD
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

=======
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Pruebas unitarias de TypeService usando Mockito (sin base de datos)
 * Grupo 6 -
 */
>>>>>>> origin/main
@Slf4j
@SpringBootTest
public class TypeServiceMockitoTest {

    @Autowired
    private TypeService typeService;

    @MockitoBean
<<<<<<< HEAD
    private TypeRepository typeRepository;

    @MockitoBean
    private PetRepository petRepository;

    //Reporte de cantidad de mascotas por tipo

    @Test
    public void testGetPetCountByType() {

        Type perro = Type.builder().id(1).name("Perro").active(true).build();
        Type gato = Type.builder().id(2).name("Gato").active(true).build();

        Mockito.when(this.typeRepository.findAll())
                .thenReturn(List.of(perro, gato));
        Mockito.when(this.petRepository.countByTypeId(1)).thenReturn(3L);
        Mockito.when(this.petRepository.countByTypeId(2)).thenReturn(1L);

        List<TypePetCountDTO> report = this.typeService.getPetCountByType();
        log.info("Reporte: {}", report);

        assertEquals(2, report.size());
        assertEquals("Perro", report.get(0).getTypeName());
        assertEquals(3L, report.get(0).getPetCount());
        assertEquals("Gato", report.get(1).getTypeName());
        assertEquals(1L, report.get(1).getPetCount());
    }

    //Devuelve lista vacia si no hay tipos

    @Test
    public void testGetPetCountByType_Empty() {

        Mockito.when(this.typeRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<TypePetCountDTO> report = this.typeService.getPetCountByType();

        assertNotNull(report);
        assertTrue(report.isEmpty());
        Mockito.verify(this.petRepository, Mockito.never()).countByTypeId(Mockito.anyInt());
    }

    //Eliminar tipo

    @Test
    public void testDeleteType() {

        Type type = Type.builder().id(10).name("Hamster").active(true).build();

        Mockito.when(this.typeRepository.findById(10))
                .thenReturn(Optional.of(type));

        assertDoesNotThrow(() -> this.typeService.delete(10));

        Mockito.verify(this.typeRepository).delete(type);
=======
    private TypeRepository repository;

    /**
     * Crear un tipo de mascota
     */
    @Test
    public void testCreateType() {

        Type newType = new Type(null, "ferret", "Small mustelid", true, "small", 7, "high");
        Type newTypeCreated = new Type(1000, "ferret", "Small mustelid", true, "small", 7, "high");

        Mockito.when(this.repository.save(newType))
                .thenReturn(newTypeCreated);

        TypeDTO typeDTO = TypeDTO.builder()
                .name("ferret")
                .description("Small mustelid")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(7)
                .careLevel("high")
                .build();

        TypeDTO typeDTOCreated = this.typeService.create(typeDTO);
        log.info("TYPE CREATED : {}", typeDTOCreated);

        assertNotNull(typeDTOCreated.getId());
        assertEquals(1000, typeDTOCreated.getId());
        assertEquals("ferret", typeDTOCreated.getName());
        assertEquals("Small mustelid", typeDTOCreated.getDescription());
        Mockito.verify(this.repository, Mockito.times(1)).save(newType);
    }

    /**
     * Actualizar un tipo de mascota
     */
    @Test
    public void testUpdateType() {

        String UP_TYPE_NAME = "parrot2";
        String UP_DESCRIPTION = "Large tropical bird";

        Type typeUpdated = new Type(2000, UP_TYPE_NAME, UP_DESCRIPTION, true, "small", 50, "high");

        Mockito.when(this.repository.save(any(Type.class)))
                .thenReturn(typeUpdated);

        TypeDTO typeDTO = TypeDTO.builder()
                .id(2000)
                .name(UP_TYPE_NAME)
                .description(UP_DESCRIPTION)
                .active(true)
                .sizeCategory("small")
                .averageLifespan(50)
                .careLevel("high")
                .build();

        TypeDTO typeDTOUpdated = this.typeService.update(typeDTO);
        log.info("TYPE UPDATED : {}", typeDTOUpdated);

        assertEquals(2000, typeDTOUpdated.getId());
        assertEquals(UP_TYPE_NAME, typeDTOUpdated.getName());
        assertEquals(UP_DESCRIPTION, typeDTOUpdated.getDescription());
        assertEquals(50, typeDTOUpdated.getAverageLifespan());
        Mockito.verify(this.repository, Mockito.times(1)).save(any(Type.class));
    }

    /**
     * Buscar un tipo existente por ID
     */
    @Test
    public void testFindTypeById() {

        Type typeExpected = new Type(1, "cat", "Domestic feline", true, "small", 15, "medium");

        Mockito.when(this.repository.findById(1))
                .thenReturn(Optional.of(typeExpected));

        TypeDTO type = null;
        try {
            type = this.typeService.findById(1);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("TYPE FOUND : {}", type);

        assertEquals(typeExpected.getId(), type.getId());
        assertEquals(typeExpected.getName(), type.getName());
        assertEquals(typeExpected.getDescription(), type.getDescription());
        Mockito.verify(this.repository).findById(1);
>>>>>>> origin/main
    }
}
