package com.paqueteria.domain;

import static com.paqueteria.domain.RecepcionistaTestSamples.*;
import static com.paqueteria.domain.SucursalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecepcionistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recepcionista.class);
        Recepcionista recepcionista1 = getRecepcionistaSample1();
        Recepcionista recepcionista2 = new Recepcionista();
        assertThat(recepcionista1).isNotEqualTo(recepcionista2);

        recepcionista2.setId(recepcionista1.getId());
        assertThat(recepcionista1).isEqualTo(recepcionista2);

        recepcionista2 = getRecepcionistaSample2();
        assertThat(recepcionista1).isNotEqualTo(recepcionista2);
    }

    @Test
    void sucursalTest() {
        Recepcionista recepcionista = getRecepcionistaRandomSampleGenerator();
        Sucursal sucursalBack = getSucursalRandomSampleGenerator();

        recepcionista.setSucursal(sucursalBack);
        assertThat(recepcionista.getSucursal()).isEqualTo(sucursalBack);

        recepcionista.sucursal(null);
        assertThat(recepcionista.getSucursal()).isNull();
    }
}
