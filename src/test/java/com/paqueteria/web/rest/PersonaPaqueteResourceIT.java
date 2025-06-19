package com.paqueteria.web.rest;

import static com.paqueteria.domain.PersonaPaqueteAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.PersonaPaquete;
import com.paqueteria.repository.PersonaPaqueteRepository;
import com.paqueteria.service.dto.PersonaPaqueteDTO;
import com.paqueteria.service.mapper.PersonaPaqueteMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonaPaqueteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonaPaqueteResourceIT {

    private static final String DEFAULT_CI = "AAAAAAAAAA";
    private static final String UPDATED_CI = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/persona-paquetes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PersonaPaqueteRepository personaPaqueteRepository;

    @Autowired
    private PersonaPaqueteMapper personaPaqueteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaPaqueteMockMvc;

    private PersonaPaquete personaPaquete;

    private PersonaPaquete insertedPersonaPaquete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaPaquete createEntity() {
        return new PersonaPaquete().ci(DEFAULT_CI).nombre(DEFAULT_NOMBRE).telefono(DEFAULT_TELEFONO).direccion(DEFAULT_DIRECCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaPaquete createUpdatedEntity() {
        return new PersonaPaquete().ci(UPDATED_CI).nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).direccion(UPDATED_DIRECCION);
    }

    @BeforeEach
    void initTest() {
        personaPaquete = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPersonaPaquete != null) {
            personaPaqueteRepository.delete(insertedPersonaPaquete);
            insertedPersonaPaquete = null;
        }
    }

    @Test
    @Transactional
    void createPersonaPaquete() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);
        var returnedPersonaPaqueteDTO = om.readValue(
            restPersonaPaqueteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personaPaqueteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PersonaPaqueteDTO.class
        );

        // Validate the PersonaPaquete in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPersonaPaquete = personaPaqueteMapper.toEntity(returnedPersonaPaqueteDTO);
        assertPersonaPaqueteUpdatableFieldsEquals(returnedPersonaPaquete, getPersistedPersonaPaquete(returnedPersonaPaquete));

        insertedPersonaPaquete = returnedPersonaPaquete;
    }

    @Test
    @Transactional
    void createPersonaPaqueteWithExistingId() throws Exception {
        // Create the PersonaPaquete with an existing ID
        personaPaquete.setId(1L);
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personaPaqueteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCiIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        personaPaquete.setCi(null);

        // Create the PersonaPaquete, which fails.
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        restPersonaPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personaPaqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        personaPaquete.setNombre(null);

        // Create the PersonaPaquete, which fails.
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        restPersonaPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personaPaqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        personaPaquete.setDireccion(null);

        // Create the PersonaPaquete, which fails.
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        restPersonaPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personaPaqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonaPaquetes() throws Exception {
        // Initialize the database
        insertedPersonaPaquete = personaPaqueteRepository.saveAndFlush(personaPaquete);

        // Get all the personaPaqueteList
        restPersonaPaqueteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personaPaquete.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @Test
    @Transactional
    void getPersonaPaquete() throws Exception {
        // Initialize the database
        insertedPersonaPaquete = personaPaqueteRepository.saveAndFlush(personaPaquete);

        // Get the personaPaquete
        restPersonaPaqueteMockMvc
            .perform(get(ENTITY_API_URL_ID, personaPaquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personaPaquete.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getNonExistingPersonaPaquete() throws Exception {
        // Get the personaPaquete
        restPersonaPaqueteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonaPaquete() throws Exception {
        // Initialize the database
        insertedPersonaPaquete = personaPaqueteRepository.saveAndFlush(personaPaquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the personaPaquete
        PersonaPaquete updatedPersonaPaquete = personaPaqueteRepository.findById(personaPaquete.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPersonaPaquete are not directly saved in db
        em.detach(updatedPersonaPaquete);
        updatedPersonaPaquete.ci(UPDATED_CI).nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).direccion(UPDATED_DIRECCION);
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(updatedPersonaPaquete);

        restPersonaPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personaPaqueteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personaPaqueteDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPersonaPaqueteToMatchAllProperties(updatedPersonaPaquete);
    }

    @Test
    @Transactional
    void putNonExistingPersonaPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personaPaquete.setId(longCount.incrementAndGet());

        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personaPaqueteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personaPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonaPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personaPaquete.setId(longCount.incrementAndGet());

        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(personaPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonaPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personaPaquete.setId(longCount.incrementAndGet());

        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaPaqueteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(personaPaqueteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonaPaqueteWithPatch() throws Exception {
        // Initialize the database
        insertedPersonaPaquete = personaPaqueteRepository.saveAndFlush(personaPaquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the personaPaquete using partial update
        PersonaPaquete partialUpdatedPersonaPaquete = new PersonaPaquete();
        partialUpdatedPersonaPaquete.setId(personaPaquete.getId());

        partialUpdatedPersonaPaquete.telefono(UPDATED_TELEFONO);

        restPersonaPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaPaquete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPersonaPaquete))
            )
            .andExpect(status().isOk());

        // Validate the PersonaPaquete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonaPaqueteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPersonaPaquete, personaPaquete),
            getPersistedPersonaPaquete(personaPaquete)
        );
    }

    @Test
    @Transactional
    void fullUpdatePersonaPaqueteWithPatch() throws Exception {
        // Initialize the database
        insertedPersonaPaquete = personaPaqueteRepository.saveAndFlush(personaPaquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the personaPaquete using partial update
        PersonaPaquete partialUpdatedPersonaPaquete = new PersonaPaquete();
        partialUpdatedPersonaPaquete.setId(personaPaquete.getId());

        partialUpdatedPersonaPaquete.ci(UPDATED_CI).nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).direccion(UPDATED_DIRECCION);

        restPersonaPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaPaquete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPersonaPaquete))
            )
            .andExpect(status().isOk());

        // Validate the PersonaPaquete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonaPaqueteUpdatableFieldsEquals(partialUpdatedPersonaPaquete, getPersistedPersonaPaquete(partialUpdatedPersonaPaquete));
    }

    @Test
    @Transactional
    void patchNonExistingPersonaPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personaPaquete.setId(longCount.incrementAndGet());

        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personaPaqueteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(personaPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonaPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personaPaquete.setId(longCount.incrementAndGet());

        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(personaPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonaPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        personaPaquete.setId(longCount.incrementAndGet());

        // Create the PersonaPaquete
        PersonaPaqueteDTO personaPaqueteDTO = personaPaqueteMapper.toDto(personaPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaPaqueteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(personaPaqueteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonaPaquete() throws Exception {
        // Initialize the database
        insertedPersonaPaquete = personaPaqueteRepository.saveAndFlush(personaPaquete);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the personaPaquete
        restPersonaPaqueteMockMvc
            .perform(delete(ENTITY_API_URL_ID, personaPaquete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return personaPaqueteRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected PersonaPaquete getPersistedPersonaPaquete(PersonaPaquete personaPaquete) {
        return personaPaqueteRepository.findById(personaPaquete.getId()).orElseThrow();
    }

    protected void assertPersistedPersonaPaqueteToMatchAllProperties(PersonaPaquete expectedPersonaPaquete) {
        assertPersonaPaqueteAllPropertiesEquals(expectedPersonaPaquete, getPersistedPersonaPaquete(expectedPersonaPaquete));
    }

    protected void assertPersistedPersonaPaqueteToMatchUpdatableProperties(PersonaPaquete expectedPersonaPaquete) {
        assertPersonaPaqueteAllUpdatablePropertiesEquals(expectedPersonaPaquete, getPersistedPersonaPaquete(expectedPersonaPaquete));
    }
}
