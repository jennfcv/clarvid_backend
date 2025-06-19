package com.paqueteria.domain;

import static com.paqueteria.domain.PaqueteTestSamples.*;
import static com.paqueteria.domain.SeguimientoPaqueteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeguimientoPaqueteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeguimientoPaquete.class);
        SeguimientoPaquete seguimientoPaquete1 = getSeguimientoPaqueteSample1();
        SeguimientoPaquete seguimientoPaquete2 = new SeguimientoPaquete();
        assertThat(seguimientoPaquete1).isNotEqualTo(seguimientoPaquete2);

        seguimientoPaquete2.setId(seguimientoPaquete1.getId());
        assertThat(seguimientoPaquete1).isEqualTo(seguimientoPaquete2);

        seguimientoPaquete2 = getSeguimientoPaqueteSample2();
        assertThat(seguimientoPaquete1).isNotEqualTo(seguimientoPaquete2);
    }

    @Test
    void paqueteTest() {
        SeguimientoPaquete seguimientoPaquete = getSeguimientoPaqueteRandomSampleGenerator();
        Paquete paqueteBack = getPaqueteRandomSampleGenerator();

        seguimientoPaquete.setPaquete(paqueteBack);
        assertThat(seguimientoPaquete.getPaquete()).isEqualTo(paqueteBack);

        seguimientoPaquete.paquete(null);
        assertThat(seguimientoPaquete.getPaquete()).isNull();
    }
}
