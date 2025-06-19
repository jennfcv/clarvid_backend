package com.paqueteria.service.mapper;

import static com.paqueteria.domain.CalificacionEntregaAsserts.*;
import static com.paqueteria.domain.CalificacionEntregaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalificacionEntregaMapperTest {

    private CalificacionEntregaMapper calificacionEntregaMapper;

    @BeforeEach
    void setUp() {
        calificacionEntregaMapper = new CalificacionEntregaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCalificacionEntregaSample1();
        var actual = calificacionEntregaMapper.toEntity(calificacionEntregaMapper.toDto(expected));
        assertCalificacionEntregaAllPropertiesEquals(expected, actual);
    }
}
