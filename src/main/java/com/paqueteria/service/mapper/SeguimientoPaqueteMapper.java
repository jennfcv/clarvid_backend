package com.paqueteria.service.mapper;

import com.paqueteria.domain.Paquete;
import com.paqueteria.domain.SeguimientoPaquete;
import com.paqueteria.service.dto.PaqueteDTO;
import com.paqueteria.service.dto.SeguimientoPaqueteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SeguimientoPaquete} and its DTO {@link SeguimientoPaqueteDTO}.
 */
@Mapper(componentModel = "spring")
public interface SeguimientoPaqueteMapper extends EntityMapper<SeguimientoPaqueteDTO, SeguimientoPaquete> {
    @Mapping(target = "paquete", source = "paquete", qualifiedByName = "paqueteId")
    SeguimientoPaqueteDTO toDto(SeguimientoPaquete s);

    @Named("paqueteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaqueteDTO toDtoPaqueteId(Paquete paquete);
}
