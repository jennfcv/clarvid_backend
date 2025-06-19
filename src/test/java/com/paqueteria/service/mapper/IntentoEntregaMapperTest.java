package com.paqueteria.service.mapper;

import static com.paqueteria.domain.IntentoEntregaAsserts.*;
import static com.paqueteria.domain.IntentoEntregaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntentoEntregaMapperTest {

    private IntentoEntregaMapper intentoEntregaMapper;

    @BeforeEach
    void setUp() {
        intentoEntregaMapper = new IntentoEntregaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getIntentoEntregaSample1();
        var actual = intentoEntregaMapper.toEntity(intentoEntregaMapper.toDto(expected));
        assertIntentoEntregaAllPropertiesEquals(expected, actual);
    }
}
