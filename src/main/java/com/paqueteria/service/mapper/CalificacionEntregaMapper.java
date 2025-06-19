package com.paqueteria.service.mapper;

import com.paqueteria.domain.CalificacionEntrega;
import com.paqueteria.domain.Paquete;
import com.paqueteria.service.dto.CalificacionEntregaDTO;
import com.paqueteria.service.dto.PaqueteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalificacionEntrega} and its DTO {@link CalificacionEntregaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalificacionEntregaMapper extends EntityMapper<CalificacionEntregaDTO, CalificacionEntrega> {
    @Mapping(target = "paquete", source = "paquete", qualifiedByName = "paqueteId")
    CalificacionEntregaDTO toDto(CalificacionEntrega s);

    @Named("paqueteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaqueteDTO toDtoPaqueteId(Paquete paquete);
}
