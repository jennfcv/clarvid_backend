package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepartidorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepartidorDTO.class);
        RepartidorDTO repartidorDTO1 = new RepartidorDTO();
        repartidorDTO1.setId(1L);
        RepartidorDTO repartidorDTO2 = new RepartidorDTO();
        assertThat(repartidorDTO1).isNotEqualTo(repartidorDTO2);
        repartidorDTO2.setId(repartidorDTO1.getId());
        assertThat(repartidorDTO1).isEqualTo(repartidorDTO2);
        repartidorDTO2.setId(2L);
        assertThat(repartidorDTO1).isNotEqualTo(repartidorDTO2);
        repartidorDTO1.setId(null);
        assertThat(repartidorDTO1).isNotEqualTo(repartidorDTO2);
    }
}
