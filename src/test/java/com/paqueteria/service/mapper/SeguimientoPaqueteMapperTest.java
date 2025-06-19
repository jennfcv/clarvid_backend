package com.paqueteria.service.mapper;

import static com.paqueteria.domain.SeguimientoPaqueteAsserts.*;
import static com.paqueteria.domain.SeguimientoPaqueteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeguimientoPaqueteMapperTest {

    private SeguimientoPaqueteMapper seguimientoPaqueteMapper;

    @BeforeEach
    void setUp() {
        seguimientoPaqueteMapper = new SeguimientoPaqueteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSeguimientoPaqueteSample1();
        var actual = seguimientoPaqueteMapper.toEntity(seguimientoPaqueteMapper.toDto(expected));
        assertSeguimientoPaqueteAllPropertiesEquals(expected, actual);
    }
}
