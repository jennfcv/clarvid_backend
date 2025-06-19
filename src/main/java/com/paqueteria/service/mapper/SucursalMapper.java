package com.paqueteria.service.mapper;

import com.paqueteria.domain.Sucursal;
import com.paqueteria.service.dto.SucursalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sucursal} and its DTO {@link SucursalDTO}.
 */
@Mapper(componentModel = "spring")
public interface SucursalMapper extends EntityMapper<SucursalDTO, Sucursal> {}
