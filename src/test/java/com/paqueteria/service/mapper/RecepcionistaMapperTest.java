package com.paqueteria.service.mapper;

import static com.paqueteria.domain.RecepcionistaAsserts.*;
import static com.paqueteria.domain.RecepcionistaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecepcionistaMapperTest {

    private RecepcionistaMapper recepcionistaMapper;

    @BeforeEach
    void setUp() {
        recepcionistaMapper = new RecepcionistaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecepcionistaSample1();
        var actual = recepcionistaMapper.toEntity(recepcionistaMapper.toDto(expected));
        assertRecepcionistaAllPropertiesEquals(expected, actual);
    }
}
