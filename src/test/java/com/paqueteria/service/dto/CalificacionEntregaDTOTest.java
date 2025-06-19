package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalificacionEntregaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalificacionEntregaDTO.class);
        CalificacionEntregaDTO calificacionEntregaDTO1 = new CalificacionEntregaDTO();
        calificacionEntregaDTO1.setId(1L);
        CalificacionEntregaDTO calificacionEntregaDTO2 = new CalificacionEntregaDTO();
        assertThat(calificacionEntregaDTO1).isNotEqualTo(calificacionEntregaDTO2);
        calificacionEntregaDTO2.setId(calificacionEntregaDTO1.getId());
        assertThat(calificacionEntregaDTO1).isEqualTo(calificacionEntregaDTO2);
        calificacionEntregaDTO2.setId(2L);
        assertThat(calificacionEntregaDTO1).isNotEqualTo(calificacionEntregaDTO2);
        calificacionEntregaDTO1.setId(null);
        assertThat(calificacionEntregaDTO1).isNotEqualTo(calificacionEntregaDTO2);
    }
}
