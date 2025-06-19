package com.paqueteria.domain;

import static com.paqueteria.domain.IntentoEntregaTestSamples.*;
import static com.paqueteria.domain.PaqueteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntentoEntregaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntentoEntrega.class);
        IntentoEntrega intentoEntrega1 = getIntentoEntregaSample1();
        IntentoEntrega intentoEntrega2 = new IntentoEntrega();
        assertThat(intentoEntrega1).isNotEqualTo(intentoEntrega2);

        intentoEntrega2.setId(intentoEntrega1.getId());
        assertThat(intentoEntrega1).isEqualTo(intentoEntrega2);

        intentoEntrega2 = getIntentoEntregaSample2();
        assertThat(intentoEntrega1).isNotEqualTo(intentoEntrega2);
    }

    @Test
    void paqueteTest() {
        IntentoEntrega intentoEntrega = getIntentoEntregaRandomSampleGenerator();
        Paquete paqueteBack = getPaqueteRandomSampleGenerator();

        intentoEntrega.setPaquete(paqueteBack);
        assertThat(intentoEntrega.getPaquete()).isEqualTo(paqueteBack);

        intentoEntrega.paquete(null);
        assertThat(intentoEntrega.getPaquete()).isNull();
    }
}
