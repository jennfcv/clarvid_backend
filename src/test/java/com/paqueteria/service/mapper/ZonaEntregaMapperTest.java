package com.paqueteria.service.mapper;

import static com.paqueteria.domain.ZonaEntregaAsserts.*;
import static com.paqueteria.domain.ZonaEntregaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZonaEntregaMapperTest {

    private ZonaEntregaMapper zonaEntregaMapper;

    @BeforeEach
    void setUp() {
        zonaEntregaMapper = new ZonaEntregaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getZonaEntregaSample1();
        var actual = zonaEntregaMapper.toEntity(zonaEntregaMapper.toDto(expected));
        assertZonaEntregaAllPropertiesEquals(expected, actual);
    }
}
