package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UbicacionRepartidorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UbicacionRepartidorDTO.class);
        UbicacionRepartidorDTO ubicacionRepartidorDTO1 = new UbicacionRepartidorDTO();
        ubicacionRepartidorDTO1.setId(1L);
        UbicacionRepartidorDTO ubicacionRepartidorDTO2 = new UbicacionRepartidorDTO();
        assertThat(ubicacionRepartidorDTO1).isNotEqualTo(ubicacionRepartidorDTO2);
        ubicacionRepartidorDTO2.setId(ubicacionRepartidorDTO1.getId());
        assertThat(ubicacionRepartidorDTO1).isEqualTo(ubicacionRepartidorDTO2);
        ubicacionRepartidorDTO2.setId(2L);
        assertThat(ubicacionRepartidorDTO1).isNotEqualTo(ubicacionRepartidorDTO2);
        ubicacionRepartidorDTO1.setId(null);
        assertThat(ubicacionRepartidorDTO1).isNotEqualTo(ubicacionRepartidorDTO2);
    }
}
