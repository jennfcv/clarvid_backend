package com.paqueteria.service.mapper;

import static com.paqueteria.domain.RepartidorAsserts.*;
import static com.paqueteria.domain.RepartidorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepartidorMapperTest {

    private RepartidorMapper repartidorMapper;

    @BeforeEach
    void setUp() {
        repartidorMapper = new RepartidorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRepartidorSample1();
        var actual = repartidorMapper.toEntity(repartidorMapper.toDto(expected));
        assertRepartidorAllPropertiesEquals(expected, actual);
    }
}
