package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Reporte: cantidad de mascotas por tipo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypePetCountDTO {

    private Integer typeId;
    private String typeName;
    private long petCount;
}