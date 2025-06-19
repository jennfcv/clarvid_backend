package com.paqueteria.service.mapper;

import com.paqueteria.domain.Paquete;
import com.paqueteria.domain.ReportePDF;
import com.paqueteria.service.dto.PaqueteDTO;
import com.paqueteria.service.dto.ReportePDFDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportePDF} and its DTO {@link ReportePDFDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportePDFMapper extends EntityMapper<ReportePDFDTO, ReportePDF> {
    @Mapping(target = "paquete", source = "paquete", qualifiedByName = "paqueteId")
    ReportePDFDTO toDto(ReportePDF s);

    @Named("paqueteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaqueteDTO toDtoPaqueteId(Paquete paquete);
}
