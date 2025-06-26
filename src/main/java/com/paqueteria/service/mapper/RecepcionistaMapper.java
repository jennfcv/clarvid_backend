package com.paqueteria.service.mapper;

import com.paqueteria.domain.Recepcionista;
import com.paqueteria.domain.Sucursal;
import com.paqueteria.service.dto.RecepcionistaDTO;
import com.paqueteria.service.dto.SucursalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recepcionista} and its DTO {@link RecepcionistaDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecepcionistaMapper extends EntityMapper<RecepcionistaDTO, Recepcionista> {

    @Override
    @Mapping(target = "sucursal", source = "sucursal", qualifiedByName = "sucursalId")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "ci", source = "ci")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "apellido", source = "apellido")
    RecepcionistaDTO toDto(Recepcionista entity);

    @Named("sucursalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SucursalDTO toDtoSucursalId(Sucursal sucursal);
}
