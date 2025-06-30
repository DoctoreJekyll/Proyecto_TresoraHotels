package com.atm.buenas_practicas_java.services.templateMethod;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractTemplateMapper<ENTITY, DTO>
{
    // Métodos abstractos de conversión directa
    public abstract DTO toDto(ENTITY e);
    public abstract ENTITY toEntity(DTO dto);

    // List<DTO> → List<ENTITY>
    public List<ENTITY> toEntity(List<DTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    // List<ENTITY> → List<DTO>
    public List<DTO> toDto(List<ENTITY> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    // Set<ENTITY> → Set<DTO>
    public Set<DTO> toDtoSet(Set<ENTITY> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    // Set<DTO> → Set<ENTITY>
    public Set<ENTITY> toEntity(Set<DTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
