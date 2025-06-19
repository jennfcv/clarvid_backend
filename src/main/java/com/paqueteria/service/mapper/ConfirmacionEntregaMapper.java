package com.paqueteria.service.mapper;

import com.paqueteria.domain.ConfirmacionEntrega;
import com.paqueteria.domain.Paquete;
import com.paqueteria.service.dto.ConfirmacionEntregaDTO;
import com.paqueteria.service.dto.PaqueteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfirmacionEntrega} and its DTO {@link ConfirmacionEntregaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfirmacionEntregaMapper extends EntityMapper<ConfirmacionEntregaDTO, ConfirmacionEntrega> {
    @Mapping(target = "paquete", source = "paquete", qualifiedByName = "paqueteId")
    ConfirmacionEntregaDTO toDto(ConfirmacionEntrega s);

    @Named("paqueteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaqueteDTO toDtoPaqueteId(Paquete paquete);
}
