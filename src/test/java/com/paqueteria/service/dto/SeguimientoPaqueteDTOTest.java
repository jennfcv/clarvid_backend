package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeguimientoPaqueteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguimientoPaqueteDTO.class);
        SeguimientoPaqueteDTO seguimientoPaqueteDTO1 = new SeguimientoPaqueteDTO();
        seguimientoPaqueteDTO1.setId(1L);
        SeguimientoPaqueteDTO seguimientoPaqueteDTO2 = new SeguimientoPaqueteDTO();
        assertThat(seguimientoPaqueteDTO1).isNotEqualTo(seguimientoPaqueteDTO2);
        seguimientoPaqueteDTO2.setId(seguimientoPaqueteDTO1.getId());
        assertThat(seguimientoPaqueteDTO1).isEqualTo(seguimientoPaqueteDTO2);
        seguimientoPaqueteDTO2.setId(2L);
        assertThat(seguimientoPaqueteDTO1).isNotEqualTo(seguimientoPaqueteDTO2);
        seguimientoPaqueteDTO1.setId(null);
        assertThat(seguimientoPaqueteDTO1).isNotEqualTo(seguimientoPaqueteDTO2);
    }
}
