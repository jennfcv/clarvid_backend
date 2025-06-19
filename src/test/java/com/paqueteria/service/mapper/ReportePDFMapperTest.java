package com.paqueteria.service.mapper;

import static com.paqueteria.domain.ReportePDFAsserts.*;
import static com.paqueteria.domain.ReportePDFTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportePDFMapperTest {

    private ReportePDFMapper reportePDFMapper;

    @BeforeEach
    void setUp() {
        reportePDFMapper = new ReportePDFMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getReportePDFSample1();
        var actual = reportePDFMapper.toEntity(reportePDFMapper.toDto(expected));
        assertReportePDFAllPropertiesEquals(expected, actual);
    }
}
