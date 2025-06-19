package com.paqueteria.service.mapper;

import com.paqueteria.domain.Repartidor;
import com.paqueteria.domain.UbicacionRepartidor;
import com.paqueteria.service.dto.RepartidorDTO;
import com.paqueteria.service.dto.UbicacionRepartidorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UbicacionRepartidor} and its DTO {@link UbicacionRepartidorDTO}.
 */
@Mapper(componentModel = "spring")
public interface UbicacionRepartidorMapper extends EntityMapper<UbicacionRepartidorDTO, UbicacionRepartidor> {
    @Mapping(target = "repartidor", source = "repartidor", qualifiedByName = "repartidorId")
    UbicacionRepartidorDTO toDto(UbicacionRepartidor s);

    @Named("repartidorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RepartidorDTO toDtoRepartidorId(Repartidor repartidor);
}
