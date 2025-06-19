package com.paqueteria.service.mapper;

import com.paqueteria.domain.IntentoEntrega;
import com.paqueteria.domain.Paquete;
import com.paqueteria.service.dto.IntentoEntregaDTO;
import com.paqueteria.service.dto.PaqueteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntentoEntrega} and its DTO {@link IntentoEntregaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntentoEntregaMapper extends EntityMapper<IntentoEntregaDTO, IntentoEntrega> {
    @Mapping(target = "paquete", source = "paquete", qualifiedByName = "paqueteId")
    IntentoEntregaDTO toDto(IntentoEntrega s);

    @Named("paqueteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaqueteDTO toDtoPaqueteId(Paquete paquete);
}
