package com.paqueteria.domain;

import static com.paqueteria.domain.SucursalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SucursalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sucursal.class);
        Sucursal sucursal1 = getSucursalSample1();
        Sucursal sucursal2 = new Sucursal();
        assertThat(sucursal1).isNotEqualTo(sucursal2);

        sucursal2.setId(sucursal1.getId());
        assertThat(sucursal1).isEqualTo(sucursal2);

        sucursal2 = getSucursalSample2();
        assertThat(sucursal1).isNotEqualTo(sucursal2);
    }
}
