package com.paqueteria.domain;

import static com.paqueteria.domain.PaqueteTestSamples.*;
import static com.paqueteria.domain.ReportePDFTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportePDFTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportePDF.class);
        ReportePDF reportePDF1 = getReportePDFSample1();
        ReportePDF reportePDF2 = new ReportePDF();
        assertThat(reportePDF1).isNotEqualTo(reportePDF2);

        reportePDF2.setId(reportePDF1.getId());
        assertThat(reportePDF1).isEqualTo(reportePDF2);

        reportePDF2 = getReportePDFSample2();
        assertThat(reportePDF1).isNotEqualTo(reportePDF2);
    }

    @Test
    void paqueteTest() {
        ReportePDF reportePDF = getReportePDFRandomSampleGenerator();
        Paquete paqueteBack = getPaqueteRandomSampleGenerator();

        reportePDF.setPaquete(paqueteBack);
        assertThat(reportePDF.getPaquete()).isEqualTo(paqueteBack);

        reportePDF.paquete(null);
        assertThat(reportePDF.getPaquete()).isNull();
    }
}
