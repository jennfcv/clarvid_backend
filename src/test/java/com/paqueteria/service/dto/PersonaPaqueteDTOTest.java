package com.paqueteria.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.paqueteria.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaPaqueteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaPaqueteDTO.class);
        PersonaPaqueteDTO personaPaqueteDTO1 = new PersonaPaqueteDTO();
        personaPaqueteDTO1.setId(1L);
        PersonaPaqueteDTO personaPaqueteDTO2 = new PersonaPaqueteDTO();
        assertThat(personaPaqueteDTO1).isNotEqualTo(personaPaqueteDTO2);
        personaPaqueteDTO2.setId(personaPaqueteDTO1.getId());
        assertThat(personaPaqueteDTO1).isEqualTo(personaPaqueteDTO2);
        personaPaqueteDTO2.setId(2L);
        assertThat(personaPaqueteDTO1).isNotEqualTo(personaPaqueteDTO2);
        personaPaqueteDTO1.setId(null);
        assertThat(personaPaqueteDTO1).isNotEqualTo(personaPaqueteDTO2);
    }
}
