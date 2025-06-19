package com.paqueteria.domain;

import static com.paqueteria.domain.RutaTestSamples.*;
import static com.paqueteria.domain.ZonaEntregaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RutaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ruta.class);
        Ruta ruta1 = getRutaSample1();
        Ruta ruta2 = new Ruta();
        assertThat(ruta1).isNotEqualTo(ruta2);

        ruta2.setId(ruta1.getId());
        assertThat(ruta1).isEqualTo(ruta2);

        ruta2 = getRutaSample2();
        assertThat(ruta1).isNotEqualTo(ruta2);
    }

    @Test
    void zonaTest() {
        Ruta ruta = getRutaRandomSampleGenerator();
        ZonaEntrega zonaEntregaBack = getZonaEntregaRandomSampleGenerator();

        ruta.setZona(zonaEntregaBack);
        assertThat(ruta.getZona()).isEqualTo(zonaEntregaBack);

        ruta.zona(null);
        assertThat(ruta.getZona()).isNull();
    }
}
