package com.paqueteria.domain;

import static com.paqueteria.domain.ConfirmacionEntregaTestSamples.*;
import static com.paqueteria.domain.PaqueteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfirmacionEntregaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfirmacionEntrega.class);
        ConfirmacionEntrega confirmacionEntrega1 = getConfirmacionEntregaSample1();
        ConfirmacionEntrega confirmacionEntrega2 = new ConfirmacionEntrega();
        assertThat(confirmacionEntrega1).isNotEqualTo(confirmacionEntrega2);

        confirmacionEntrega2.setId(confirmacionEntrega1.getId());
        assertThat(confirmacionEntrega1).isEqualTo(confirmacionEntrega2);

        confirmacionEntrega2 = getConfirmacionEntregaSample2();
        assertThat(confirmacionEntrega1).isNotEqualTo(confirmacionEntrega2);
    }

    @Test
    void paqueteTest() {
        ConfirmacionEntrega confirmacionEntrega = getConfirmacionEntregaRandomSampleGenerator();
        Paquete paqueteBack = getPaqueteRandomSampleGenerator();

        confirmacionEntrega.setPaquete(paqueteBack);
        assertThat(confirmacionEntrega.getPaquete()).isEqualTo(paqueteBack);

        confirmacionEntrega.paquete(null);
        assertThat(confirmacionEntrega.getPaquete()).isNull();
    }
}
