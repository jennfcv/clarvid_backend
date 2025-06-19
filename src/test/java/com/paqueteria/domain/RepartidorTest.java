package com.paqueteria.domain;

import static com.paqueteria.domain.RepartidorTestSamples.*;
import static com.paqueteria.domain.SucursalTestSamples.*;
import static com.paqueteria.domain.ZonaEntregaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepartidorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Repartidor.class);
        Repartidor repartidor1 = getRepartidorSample1();
        Repartidor repartidor2 = new Repartidor();
        assertThat(repartidor1).isNotEqualTo(repartidor2);

        repartidor2.setId(repartidor1.getId());
        assertThat(repartidor1).isEqualTo(repartidor2);

        repartidor2 = getRepartidorSample2();
        assertThat(repartidor1).isNotEqualTo(repartidor2);
    }

    @Test
    void zonaTest() {
        Repartidor repartidor = getRepartidorRandomSampleGenerator();
        ZonaEntrega zonaEntregaBack = getZonaEntregaRandomSampleGenerator();

        repartidor.setZona(zonaEntregaBack);
        assertThat(repartidor.getZona()).isEqualTo(zonaEntregaBack);

        repartidor.zona(null);
        assertThat(repartidor.getZona()).isNull();
    }

    @Test
    void sucursalTest() {
        Repartidor repartidor = getRepartidorRandomSampleGenerator();
        Sucursal sucursalBack = getSucursalRandomSampleGenerator();

        repartidor.setSucursal(sucursalBack);
        assertThat(repartidor.getSucursal()).isEqualTo(sucursalBack);

        repartidor.sucursal(null);
        assertThat(repartidor.getSucursal()).isNull();
    }
}
