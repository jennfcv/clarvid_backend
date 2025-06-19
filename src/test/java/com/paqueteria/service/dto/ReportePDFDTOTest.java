package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportePDFDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportePDFDTO.class);
        ReportePDFDTO reportePDFDTO1 = new ReportePDFDTO();
        reportePDFDTO1.setId(1L);
        ReportePDFDTO reportePDFDTO2 = new ReportePDFDTO();
        assertThat(reportePDFDTO1).isNotEqualTo(reportePDFDTO2);
        reportePDFDTO2.setId(reportePDFDTO1.getId());
        assertThat(reportePDFDTO1).isEqualTo(reportePDFDTO2);
        reportePDFDTO2.setId(2L);
        assertThat(reportePDFDTO1).isNotEqualTo(reportePDFDTO2);
        reportePDFDTO1.setId(null);
        assertThat(reportePDFDTO1).isNotEqualTo(reportePDFDTO2);
    }
}
