package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RutaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RutaDTO.class);
        RutaDTO rutaDTO1 = new RutaDTO();
        rutaDTO1.setId(1L);
        RutaDTO rutaDTO2 = new RutaDTO();
        assertThat(rutaDTO1).isNotEqualTo(rutaDTO2);
        rutaDTO2.setId(rutaDTO1.getId());
        assertThat(rutaDTO1).isEqualTo(rutaDTO2);
        rutaDTO2.setId(2L);
        assertThat(rutaDTO1).isNotEqualTo(rutaDTO2);
        rutaDTO1.setId(null);
        assertThat(rutaDTO1).isNotEqualTo(rutaDTO2);
    }
}
