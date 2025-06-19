package com.paqueteria.domain;

import static com.paqueteria.domain.ZonaEntregaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZonaEntregaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZonaEntrega.class);
        ZonaEntrega zonaEntrega1 = getZonaEntregaSample1();
        ZonaEntrega zonaEntrega2 = new ZonaEntrega();
        assertThat(zonaEntrega1).isNotEqualTo(zonaEntrega2);

        zonaEntrega2.setId(zonaEntrega1.getId());
        assertThat(zonaEntrega1).isEqualTo(zonaEntrega2);

        zonaEntrega2 = getZonaEntregaSample2();
        assertThat(zonaEntrega1).isNotEqualTo(zonaEntrega2);
    }
}
