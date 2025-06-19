package com.paqueteria.service.mapper;

import static com.paqueteria.domain.SucursalAsserts.*;
import static com.paqueteria.domain.SucursalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SucursalMapperTest {

    private SucursalMapper sucursalMapper;

    @BeforeEach
    void setUp() {
        sucursalMapper = new SucursalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSucursalSample1();
        var actual = sucursalMapper.toEntity(sucursalMapper.toDto(expected));
        assertSucursalAllPropertiesEquals(expected, actual);
    }
}
