package com.paqueteria.service.mapper;

import com.paqueteria.domain.Ruta;
import com.paqueteria.domain.ZonaEntrega;
import com.paqueteria.service.dto.RutaDTO;
import com.paqueteria.service.dto.ZonaEntregaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ruta} and its DTO {@link RutaDTO}.
 */
@Mapper(componentModel = "spring")
public interface RutaMapper extends EntityMapper<RutaDTO, Ruta> {
    @Mapping(target = "zona", source = "zona", qualifiedByName = "zonaEntregaId")
    RutaDTO toDto(Ruta s);

    @Named("zonaEntregaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ZonaEntregaDTO toDtoZonaEntregaId(ZonaEntrega zonaEntrega);
}
