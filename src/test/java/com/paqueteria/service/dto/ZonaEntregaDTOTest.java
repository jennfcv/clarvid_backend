package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZonaEntregaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZonaEntregaDTO.class);
        ZonaEntregaDTO zonaEntregaDTO1 = new ZonaEntregaDTO();
        zonaEntregaDTO1.setId(1L);
        ZonaEntregaDTO zonaEntregaDTO2 = new ZonaEntregaDTO();
        assertThat(zonaEntregaDTO1).isNotEqualTo(zonaEntregaDTO2);
        zonaEntregaDTO2.setId(zonaEntregaDTO1.getId());
        assertThat(zonaEntregaDTO1).isEqualTo(zonaEntregaDTO2);
        zonaEntregaDTO2.setId(2L);
        assertThat(zonaEntregaDTO1).isNotEqualTo(zonaEntregaDTO2);
        zonaEntregaDTO1.setId(null);
        assertThat(zonaEntregaDTO1).isNotEqualTo(zonaEntregaDTO2);
    }
}
