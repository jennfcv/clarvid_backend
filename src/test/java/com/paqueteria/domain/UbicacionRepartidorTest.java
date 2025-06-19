package com.paqueteria.domain;

import static com.paqueteria.domain.RepartidorTestSamples.*;
import static com.paqueteria.domain.UbicacionRepartidorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UbicacionRepartidorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UbicacionRepartidor.class);
        UbicacionRepartidor ubicacionRepartidor1 = getUbicacionRepartidorSample1();
        UbicacionRepartidor ubicacionRepartidor2 = new UbicacionRepartidor();
        assertThat(ubicacionRepartidor1).isNotEqualTo(ubicacionRepartidor2);

        ubicacionRepartidor2.setId(ubicacionRepartidor1.getId());
        assertThat(ubicacionRepartidor1).isEqualTo(ubicacionRepartidor2);

        ubicacionRepartidor2 = getUbicacionRepartidorSample2();
        assertThat(ubicacionRepartidor1).isNotEqualTo(ubicacionRepartidor2);
    }

    @Test
    void repartidorTest() {
        UbicacionRepartidor ubicacionRepartidor = getUbicacionRepartidorRandomSampleGenerator();
        Repartidor repartidorBack = getRepartidorRandomSampleGenerator();

        ubicacionRepartidor.setRepartidor(repartidorBack);
        assertThat(ubicacionRepartidor.getRepartidor()).isEqualTo(repartidorBack);

        ubicacionRepartidor.repartidor(null);
        assertThat(ubicacionRepartidor.getRepartidor()).isNull();
    }
}
