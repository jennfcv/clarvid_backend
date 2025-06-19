package com.paqueteria.service.mapper;

import static com.paqueteria.domain.PersonaPaqueteAsserts.*;
import static com.paqueteria.domain.PersonaPaqueteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaPaqueteMapperTest {

    private PersonaPaqueteMapper personaPaqueteMapper;

    @BeforeEach
    void setUp() {
        personaPaqueteMapper = new PersonaPaqueteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPersonaPaqueteSample1();
        var actual = personaPaqueteMapper.toEntity(personaPaqueteMapper.toDto(expected));
        assertPersonaPaqueteAllPropertiesEquals(expected, actual);
    }
}
