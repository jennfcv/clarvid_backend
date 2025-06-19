package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfirmacionEntregaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfirmacionEntregaDTO.class);
        ConfirmacionEntregaDTO confirmacionEntregaDTO1 = new ConfirmacionEntregaDTO();
        confirmacionEntregaDTO1.setId(1L);
        ConfirmacionEntregaDTO confirmacionEntregaDTO2 = new ConfirmacionEntregaDTO();
        assertThat(confirmacionEntregaDTO1).isNotEqualTo(confirmacionEntregaDTO2);
        confirmacionEntregaDTO2.setId(confirmacionEntregaDTO1.getId());
        assertThat(confirmacionEntregaDTO1).isEqualTo(confirmacionEntregaDTO2);
        confirmacionEntregaDTO2.setId(2L);
        assertThat(confirmacionEntregaDTO1).isNotEqualTo(confirmacionEntregaDTO2);
        confirmacionEntregaDTO1.setId(null);
        assertThat(confirmacionEntregaDTO1).isNotEqualTo(confirmacionEntregaDTO2);
    }
}
