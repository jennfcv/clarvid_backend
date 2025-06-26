package com.paqueteria.service.mapper;

import com.paqueteria.domain.Repartidor;
import com.paqueteria.domain.Sucursal;
import com.paqueteria.domain.User;
import com.paqueteria.domain.ZonaEntrega;
import com.paqueteria.service.dto.RepartidorDTO;
import com.paqueteria.service.dto.SucursalDTO;
import com.paqueteria.service.dto.UserDTO;
import com.paqueteria.service.dto.ZonaEntregaDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RepartidorMapper extends EntityMapper<RepartidorDTO, Repartidor> {

    @Override
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "userLogin")
    @Mapping(target = "zona", source = "zona", qualifiedByName = "zonaEntregaId")
    @Mapping(target = "sucursal", source = "sucursal", qualifiedByName = "sucursalId")
    RepartidorDTO toDto(Repartidor s);

    @Override
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "zona", source = "zona")
    @Mapping(target = "sucursal", source = "sucursal")
    Repartidor toEntity(RepartidorDTO repartidorDTO);


    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("zonaEntregaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ZonaEntregaDTO toDtoZonaEntregaId(ZonaEntrega zonaEntrega);

    @Named("sucursalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SucursalDTO toDtoSucursalId(Sucursal sucursal);
}
