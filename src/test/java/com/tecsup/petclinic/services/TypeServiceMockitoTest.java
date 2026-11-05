package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TypeServiceMockitoTest {

    @Autowired
    private TypeService typeService;

    @MockitoBean
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
}
