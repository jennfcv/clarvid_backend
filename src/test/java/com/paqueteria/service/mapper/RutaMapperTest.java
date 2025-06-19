package com.paqueteria.service.mapper;

import static com.paqueteria.domain.RutaAsserts.*;
import static com.paqueteria.domain.RutaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RutaMapperTest {

    private RutaMapper rutaMapper;

    @BeforeEach
    void setUp() {
        rutaMapper = new RutaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRutaSample1();
        var actual = rutaMapper.toEntity(rutaMapper.toDto(expected));
        assertRutaAllPropertiesEquals(expected, actual);
    }
}
