package com.paqueteria.service.mapper;

import static com.paqueteria.domain.ConfirmacionEntregaAsserts.*;
import static com.paqueteria.domain.ConfirmacionEntregaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfirmacionEntregaMapperTest {

    private ConfirmacionEntregaMapper confirmacionEntregaMapper;

    @BeforeEach
    void setUp() {
        confirmacionEntregaMapper = new ConfirmacionEntregaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConfirmacionEntregaSample1();
        var actual = confirmacionEntregaMapper.toEntity(confirmacionEntregaMapper.toDto(expected));
        assertConfirmacionEntregaAllPropertiesEquals(expected, actual);
    }
}
