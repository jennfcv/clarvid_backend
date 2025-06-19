package com.paqueteria.service.mapper;

import static com.paqueteria.domain.PaqueteAsserts.*;
import static com.paqueteria.domain.PaqueteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaqueteMapperTest {

    private PaqueteMapper paqueteMapper;

    @BeforeEach
    void setUp() {
        paqueteMapper = new PaqueteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaqueteSample1();
        var actual = paqueteMapper.toEntity(paqueteMapper.toDto(expected));
        assertPaqueteAllPropertiesEquals(expected, actual);
    }
}
