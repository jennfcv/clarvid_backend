package com.paqueteria.domain;

import static com.paqueteria.domain.CalificacionEntregaTestSamples.*;
import static com.paqueteria.domain.PaqueteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalificacionEntregaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalificacionEntrega.class);
        CalificacionEntrega calificacionEntrega1 = getCalificacionEntregaSample1();
        CalificacionEntrega calificacionEntrega2 = new CalificacionEntrega();
        assertThat(calificacionEntrega1).isNotEqualTo(calificacionEntrega2);

        calificacionEntrega2.setId(calificacionEntrega1.getId());
        assertThat(calificacionEntrega1).isEqualTo(calificacionEntrega2);

        calificacionEntrega2 = getCalificacionEntregaSample2();
        assertThat(calificacionEntrega1).isNotEqualTo(calificacionEntrega2);
    }

    @Test
    void paqueteTest() {
        CalificacionEntrega calificacionEntrega = getCalificacionEntregaRandomSampleGenerator();
        Paquete paqueteBack = getPaqueteRandomSampleGenerator();

        calificacionEntrega.setPaquete(paqueteBack);
        assertThat(calificacionEntrega.getPaquete()).isEqualTo(paqueteBack);

        calificacionEntrega.paquete(null);
        assertThat(calificacionEntrega.getPaquete()).isNull();
    }
}
