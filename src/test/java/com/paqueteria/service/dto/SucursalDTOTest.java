package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SucursalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SucursalDTO.class);
        SucursalDTO sucursalDTO1 = new SucursalDTO();
        sucursalDTO1.setId(1L);
        SucursalDTO sucursalDTO2 = new SucursalDTO();
        assertThat(sucursalDTO1).isNotEqualTo(sucursalDTO2);
        sucursalDTO2.setId(sucursalDTO1.getId());
        assertThat(sucursalDTO1).isEqualTo(sucursalDTO2);
        sucursalDTO2.setId(2L);
        assertThat(sucursalDTO1).isNotEqualTo(sucursalDTO2);
        sucursalDTO1.setId(null);
        assertThat(sucursalDTO1).isNotEqualTo(sucursalDTO2);
    }
}
