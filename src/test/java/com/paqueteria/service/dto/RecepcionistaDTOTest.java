package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecepcionistaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecepcionistaDTO.class);
        RecepcionistaDTO recepcionistaDTO1 = new RecepcionistaDTO();
        recepcionistaDTO1.setId(1L);
        RecepcionistaDTO recepcionistaDTO2 = new RecepcionistaDTO();
        assertThat(recepcionistaDTO1).isNotEqualTo(recepcionistaDTO2);
        recepcionistaDTO2.setId(recepcionistaDTO1.getId());
        assertThat(recepcionistaDTO1).isEqualTo(recepcionistaDTO2);
        recepcionistaDTO2.setId(2L);
        assertThat(recepcionistaDTO1).isNotEqualTo(recepcionistaDTO2);
        recepcionistaDTO1.setId(null);
        assertThat(recepcionistaDTO1).isNotEqualTo(recepcionistaDTO2);
    }
}
