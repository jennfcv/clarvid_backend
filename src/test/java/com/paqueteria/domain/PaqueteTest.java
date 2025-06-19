package com.paqueteria.domain;

import static com.paqueteria.domain.PaqueteTestSamples.*;
import static com.paqueteria.domain.PersonaPaqueteTestSamples.*;
import static com.paqueteria.domain.RutaTestSamples.*;
import static com.paqueteria.domain.SucursalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaqueteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paquete.class);
        Paquete paquete1 = getPaqueteSample1();
        Paquete paquete2 = new Paquete();
        assertThat(paquete1).isNotEqualTo(paquete2);

        paquete2.setId(paquete1.getId());
        assertThat(paquete1).isEqualTo(paquete2);

        paquete2 = getPaqueteSample2();
        assertThat(paquete1).isNotEqualTo(paquete2);
    }

    @Test
    void remitenteTest() {
        Paquete paquete = getPaqueteRandomSampleGenerator();
        PersonaPaquete personaPaqueteBack = getPersonaPaqueteRandomSampleGenerator();

        paquete.setRemitente(personaPaqueteBack);
        assertThat(paquete.getRemitente()).isEqualTo(personaPaqueteBack);

        paquete.remitente(null);
        assertThat(paquete.getRemitente()).isNull();
    }

    @Test
    void destinatarioTest() {
        Paquete paquete = getPaqueteRandomSampleGenerator();
        PersonaPaquete personaPaqueteBack = getPersonaPaqueteRandomSampleGenerator();

        paquete.setDestinatario(personaPaqueteBack);
        assertThat(paquete.getDestinatario()).isEqualTo(personaPaqueteBack);

        paquete.destinatario(null);
        assertThat(paquete.getDestinatario()).isNull();
    }

    @Test
    void rutaTest() {
        Paquete paquete = getPaqueteRandomSampleGenerator();
        Ruta rutaBack = getRutaRandomSampleGenerator();

        paquete.setRuta(rutaBack);
        assertThat(paquete.getRuta()).isEqualTo(rutaBack);

        paquete.ruta(null);
        assertThat(paquete.getRuta()).isNull();
    }

    @Test
    void sucursalOrigenTest() {
        Paquete paquete = getPaqueteRandomSampleGenerator();
        Sucursal sucursalBack = getSucursalRandomSampleGenerator();

        paquete.setSucursalOrigen(sucursalBack);
        assertThat(paquete.getSucursalOrigen()).isEqualTo(sucursalBack);

        paquete.sucursalOrigen(null);
        assertThat(paquete.getSucursalOrigen()).isNull();
    }

    @Test
    void sucursalDestinoTest() {
        Paquete paquete = getPaqueteRandomSampleGenerator();
        Sucursal sucursalBack = getSucursalRandomSampleGenerator();

        paquete.setSucursalDestino(sucursalBack);
        assertThat(paquete.getSucursalDestino()).isEqualTo(sucursalBack);

        paquete.sucursalDestino(null);
        assertThat(paquete.getSucursalDestino()).isNull();
    }
}
