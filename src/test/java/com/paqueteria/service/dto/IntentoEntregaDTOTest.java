package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntentoEntregaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntentoEntregaDTO.class);
        IntentoEntregaDTO intentoEntregaDTO1 = new IntentoEntregaDTO();
        intentoEntregaDTO1.setId(1L);
        IntentoEntregaDTO intentoEntregaDTO2 = new IntentoEntregaDTO();
        assertThat(intentoEntregaDTO1).isNotEqualTo(intentoEntregaDTO2);
        intentoEntregaDTO2.setId(intentoEntregaDTO1.getId());
        assertThat(intentoEntregaDTO1).isEqualTo(intentoEntregaDTO2);
        intentoEntregaDTO2.setId(2L);
        assertThat(intentoEntregaDTO1).isNotEqualTo(intentoEntregaDTO2);
        intentoEntregaDTO1.setId(null);
        assertThat(intentoEntregaDTO1).isNotEqualTo(intentoEntregaDTO2);
    }
}
