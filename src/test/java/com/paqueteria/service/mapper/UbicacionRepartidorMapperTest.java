package com.paqueteria.service.mapper;

import static com.paqueteria.domain.UbicacionRepartidorAsserts.*;
import static com.paqueteria.domain.UbicacionRepartidorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UbicacionRepartidorMapperTest {

    private UbicacionRepartidorMapper ubicacionRepartidorMapper;

    @BeforeEach
    void setUp() {
        ubicacionRepartidorMapper = new UbicacionRepartidorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUbicacionRepartidorSample1();
        var actual = ubicacionRepartidorMapper.toEntity(ubicacionRepartidorMapper.toDto(expected));
        assertUbicacionRepartidorAllPropertiesEquals(expected, actual);
    }
}
