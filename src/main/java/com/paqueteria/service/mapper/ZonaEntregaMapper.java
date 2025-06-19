package com.paqueteria.service.mapper;

import com.paqueteria.domain.ZonaEntrega;
import com.paqueteria.service.dto.ZonaEntregaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ZonaEntrega} and its DTO {@link ZonaEntregaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ZonaEntregaMapper extends EntityMapper<ZonaEntregaDTO, ZonaEntrega> {}
