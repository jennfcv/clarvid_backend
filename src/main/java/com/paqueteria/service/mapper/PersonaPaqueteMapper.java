package com.paqueteria.service.mapper;

import com.paqueteria.domain.PersonaPaquete;
import com.paqueteria.service.dto.PersonaPaqueteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonaPaquete} and its DTO {@link PersonaPaqueteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonaPaqueteMapper extends EntityMapper<PersonaPaqueteDTO, PersonaPaquete> {}
