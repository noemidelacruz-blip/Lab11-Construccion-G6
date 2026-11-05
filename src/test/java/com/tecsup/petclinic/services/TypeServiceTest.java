package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de TypeService contra la base de datos (H2)
 * Grupo 6 - Integrante A : CRUD básico
 */
@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    /**
     * Crear un tipo de mascota
     */
    @Test
    public void testCreateType() {

        String TYPE_NAME = "ferret";
        String DESCRIPTION = "Small mustelid";
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 7;
        String CARE_LEVEL = "high";

        TypeDTO typeDTO = TypeDTO.builder()
                .name(TYPE_NAME)
                .description(DESCRIPTION)
                .active(true)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        TypeDTO newTypeDTO = this.typeService.create(typeDTO);

        log.info("TYPE CREATED : {}", newTypeDTO);

        assertNotNull(newTypeDTO.getId());
        assertEquals(TYPE_NAME, newTypeDTO.getName());
        assertEquals(DESCRIPTION, newTypeDTO.getDescription());
        assertTrue(newTypeDTO.getActive());
        assertEquals(SIZE_CATEGORY, newTypeDTO.getSizeCategory());
        assertEquals(AVERAGE_LIFESPAN, newTypeDTO.getAverageLifespan());
        assertEquals(CARE_LEVEL, newTypeDTO.getCareLevel());
    }

    /**
     * Actualizar un tipo de mascota
     */
    @Test
    public void testUpdateType() {

        String TYPE_NAME = "parrot";
        String DESCRIPTION = "Tropical bird";

        String UP_TYPE_NAME = "parrot2";
        String UP_DESCRIPTION = "Large tropical bird";
        Integer UP_AVERAGE_LIFESPAN = 50;

        // ------------ Create ---------------
        TypeDTO typeDTO = TypeDTO.builder()
                .name(TYPE_NAME)
                .description(DESCRIPTION)
                .active(true)
                .sizeCategory("small")
                .averageLifespan(40)
                .careLevel("high")
                .build();

        TypeDTO typeDTOCreated = this.typeService.create(typeDTO);
        log.info("TYPE CREATED : {}", typeDTOCreated);

        // ------------ Update ---------------
        typeDTOCreated.setName(UP_TYPE_NAME);
        typeDTOCreated.setDescription(UP_DESCRIPTION);
        typeDTOCreated.setAverageLifespan(UP_AVERAGE_LIFESPAN);

        TypeDTO upgradeTypeDTO = this.typeService.update(typeDTOCreated);
        log.info("TYPE UPDATED : {}", upgradeTypeDTO);

        // ------------ Validate ---------------
        //            EXPECTED              ACTUAL
        assertEquals(typeDTOCreated.getId(), upgradeTypeDTO.getId());
        assertEquals(UP_TYPE_NAME, upgradeTypeDTO.getName());
        assertEquals(UP_DESCRIPTION, upgradeTypeDTO.getDescription());
        assertEquals(UP_AVERAGE_LIFESPAN, upgradeTypeDTO.getAverageLifespan());
    }

    /**
     * Buscar un tipo existente por ID (datos de data.sql)
     */
    @Test
    public void testFindTypeById() {

        Integer ID = 1;
        String NAME_EXPECTED = "cat";
        String DESCRIPTION_EXPECTED = "Domestic feline";

        TypeDTO type = null;

        try {
            type = this.typeService.findById(ID);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("TYPE FOUND : {}", type);

        assertEquals(ID, type.getId());
        assertEquals(NAME_EXPECTED, type.getName());
        assertEquals(DESCRIPTION_EXPECTED, type.getDescription());
        assertTrue(type.getActive());
    }
}
