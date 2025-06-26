package com.paqueteria.service.mapper;

import com.paqueteria.domain.Paquete;
import com.paqueteria.domain.PersonaPaquete;
import com.paqueteria.domain.Ruta;
import com.paqueteria.domain.Sucursal;
import com.paqueteria.domain.User;
import com.paqueteria.service.dto.PaqueteDTO;
import com.paqueteria.service.dto.PersonaPaqueteDTO;
import com.paqueteria.service.dto.RutaDTO;
import com.paqueteria.service.dto.SucursalDTO;
import com.paqueteria.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paquete} and its DTO {@link PaqueteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaqueteMapper extends EntityMapper<PaqueteDTO, Paquete> {

    @Mapping(target = "recepcionista", source = "recepcionista", qualifiedByName = "userLogin")
    @Mapping(target = "repartidor", source = "repartidor", qualifiedByName = "userLogin")
    @Mapping(target = "remitente", source = "remitente")
    @Mapping(target = "destinatario", source = "destinatario")
    @Mapping(target = "ruta", source = "ruta", qualifiedByName = "rutaId")
    @Mapping(target = "sucursalOrigen", source = "sucursalOrigen", qualifiedByName = "sucursalId")
    @Mapping(target = "sucursalDestino", source = "sucursalDestino", qualifiedByName = "sucursalId")
    PaqueteDTO toDto(Paquete s);

    @Mapping(target = "recepcionista", source = "recepcionista")
    @Mapping(target = "repartidor", source = "repartidor")
    @Mapping(target = "remitente", source = "remitente")
    @Mapping(target = "destinatario", source = "destinatario")
    @Mapping(target = "ruta", source = "ruta")
    @Mapping(target = "sucursalOrigen", source = "sucursalOrigen")
    @Mapping(target = "sucursalDestino", source = "sucursalDestino")
    Paquete toEntity(PaqueteDTO dto);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("rutaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RutaDTO toDtoRutaId(Ruta ruta);

    @Named("sucursalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SucursalDTO toDtoSucursalId(Sucursal sucursal);
}
