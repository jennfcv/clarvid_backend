package com.paqueteria.service.mapper;

import com.paqueteria.domain.Recepcionista;
import com.paqueteria.domain.Sucursal;
import com.paqueteria.domain.User;
import com.paqueteria.service.dto.RecepcionistaDTO;
import com.paqueteria.service.dto.SucursalDTO;
import com.paqueteria.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recepcionista} and its DTO {@link RecepcionistaDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecepcionistaMapper extends EntityMapper<RecepcionistaDTO, Recepcionista> {
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "userLogin")
    @Mapping(target = "sucursal", source = "sucursal", qualifiedByName = "sucursalId")
    RecepcionistaDTO toDto(Recepcionista s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("sucursalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SucursalDTO toDtoSucursalId(Sucursal sucursal);
}
