package com.paqueteria.domain;

import static com.paqueteria.domain.PersonaPaqueteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaPaqueteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaPaquete.class);
        PersonaPaquete personaPaquete1 = getPersonaPaqueteSample1();
        PersonaPaquete personaPaquete2 = new PersonaPaquete();
        assertThat(personaPaquete1).isNotEqualTo(personaPaquete2);

        personaPaquete2.setId(personaPaquete1.getId());
        assertThat(personaPaquete1).isEqualTo(personaPaquete2);

        personaPaquete2 = getPersonaPaqueteSample2();
        assertThat(personaPaquete1).isNotEqualTo(personaPaquete2);
    }
}
