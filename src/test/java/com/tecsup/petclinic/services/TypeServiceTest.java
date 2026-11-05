package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TypeMapper typeMapper;

    @InjectMocks
    private TypeServiceImpl typeService;

    // ================= Integrante C - Noemi: filtros y errores =================

    @Test
    public void testGetPetCountByType_ExcludeInactive() {

        Type dog = Type.builder().id(2).name("dog").active(true).build();
        Type snake = Type.builder().id(6).name("snake").active(false).build();

        when(typeRepository.findAll()).thenReturn(List.of(dog, snake));
        when(petRepository.findByTypeId(2)).thenReturn(List.of(new Pet(), new Pet()));

        List<TypePetCountDTO> report = typeService.getPetCountByType();

        assertEquals(1, report.size());
        assertEquals("dog", report.get(0).getTypeName());
        assertEquals(2, report.get(0).getPetCount());
        assertTrue(report.stream().noneMatch(r -> r.getTypeName().equals("snake")));
        verify(petRepository, never()).findByTypeId(6);
    }

    @Test
    public void testFindTypeById_NotFound() {

        when(typeRepository.findById(99)).thenReturn(Optional.empty());

        TypeNotFoundException ex = assertThrows(TypeNotFoundException.class,
                () -> typeService.findById(99));

        assertEquals("Type not found...!", ex.getMessage());
        verify(typeRepository).findById(99);
    }

    @Test
    public void testDeleteType_NotFound() {

        when(typeRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(TypeNotFoundException.class, () -> typeService.delete(99));

        verify(typeRepository, never()).delete(any(Type.class));
    }
}