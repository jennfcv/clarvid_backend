package com.paqueteria.web.rest;

import static com.paqueteria.domain.SeguimientoPaqueteAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.SeguimientoPaquete;
import com.paqueteria.repository.SeguimientoPaqueteRepository;
import com.paqueteria.service.dto.SeguimientoPaqueteDTO;
import com.paqueteria.service.mapper.SeguimientoPaqueteMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link SeguimientoPaqueteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeguimientoPaqueteResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/seguimiento-paquetes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SeguimientoPaqueteRepository seguimientoPaqueteRepository;

    @Autowired
    private SeguimientoPaqueteMapper seguimientoPaqueteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeguimientoPaqueteMockMvc;

    private SeguimientoPaquete seguimientoPaquete;

    private SeguimientoPaquete insertedSeguimientoPaquete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeguimientoPaquete createEntity() {
        return new SeguimientoPaquete()
            .estado(DEFAULT_ESTADO)
            .fecha(DEFAULT_FECHA)
            .ubicacion(DEFAULT_UBICACION)
            .observaciones(DEFAULT_OBSERVACIONES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SeguimientoPaquete createUpdatedEntity() {
        return new SeguimientoPaquete()
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA)
            .ubicacion(UPDATED_UBICACION)
            .observaciones(UPDATED_OBSERVACIONES);
    }

    @BeforeEach
    void initTest() {
        seguimientoPaquete = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSeguimientoPaquete != null) {
            seguimientoPaqueteRepository.delete(insertedSeguimientoPaquete);
            insertedSeguimientoPaquete = null;
        }
    }

    @Test
    @Transactional
    void createSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);
        var returnedSeguimientoPaqueteDTO = om.readValue(
            restSeguimientoPaqueteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seguimientoPaqueteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SeguimientoPaqueteDTO.class
        );

        // Validate the SeguimientoPaquete in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSeguimientoPaquete = seguimientoPaqueteMapper.toEntity(returnedSeguimientoPaqueteDTO);
        assertSeguimientoPaqueteUpdatableFieldsEquals(
            returnedSeguimientoPaquete,
            getPersistedSeguimientoPaquete(returnedSeguimientoPaquete)
        );

        insertedSeguimientoPaquete = returnedSeguimientoPaquete;
    }

    @Test
    @Transactional
    void createSeguimientoPaqueteWithExistingId() throws Exception {
        // Create the SeguimientoPaquete with an existing ID
        seguimientoPaquete.setId(1L);
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguimientoPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seguimientoPaqueteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        seguimientoPaquete.setEstado(null);

        // Create the SeguimientoPaquete, which fails.
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        restSeguimientoPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seguimientoPaqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        seguimientoPaquete.setFecha(null);

        // Create the SeguimientoPaquete, which fails.
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        restSeguimientoPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seguimientoPaqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSeguimientoPaquetes() throws Exception {
        // Initialize the database
        insertedSeguimientoPaquete = seguimientoPaqueteRepository.saveAndFlush(seguimientoPaquete);

        // Get all the seguimientoPaqueteList
        restSeguimientoPaqueteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguimientoPaquete.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }

    @Test
    @Transactional
    void getSeguimientoPaquete() throws Exception {
        // Initialize the database
        insertedSeguimientoPaquete = seguimientoPaqueteRepository.saveAndFlush(seguimientoPaquete);

        // Get the seguimientoPaquete
        restSeguimientoPaqueteMockMvc
            .perform(get(ENTITY_API_URL_ID, seguimientoPaquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seguimientoPaquete.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }

    @Test
    @Transactional
    void getNonExistingSeguimientoPaquete() throws Exception {
        // Get the seguimientoPaquete
        restSeguimientoPaqueteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSeguimientoPaquete() throws Exception {
        // Initialize the database
        insertedSeguimientoPaquete = seguimientoPaqueteRepository.saveAndFlush(seguimientoPaquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the seguimientoPaquete
        SeguimientoPaquete updatedSeguimientoPaquete = seguimientoPaqueteRepository.findById(seguimientoPaquete.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSeguimientoPaquete are not directly saved in db
        em.detach(updatedSeguimientoPaquete);
        updatedSeguimientoPaquete
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA)
            .ubicacion(UPDATED_UBICACION)
            .observaciones(UPDATED_OBSERVACIONES);
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(updatedSeguimientoPaquete);

        restSeguimientoPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seguimientoPaqueteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(seguimientoPaqueteDTO))
            )
            .andExpect(status().isOk());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSeguimientoPaqueteToMatchAllProperties(updatedSeguimientoPaquete);
    }

    @Test
    @Transactional
    void putNonExistingSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seguimientoPaquete.setId(longCount.incrementAndGet());

        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguimientoPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seguimientoPaqueteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(seguimientoPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seguimientoPaquete.setId(longCount.incrementAndGet());

        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguimientoPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(seguimientoPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seguimientoPaquete.setId(longCount.incrementAndGet());

        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguimientoPaqueteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(seguimientoPaqueteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeguimientoPaqueteWithPatch() throws Exception {
        // Initialize the database
        insertedSeguimientoPaquete = seguimientoPaqueteRepository.saveAndFlush(seguimientoPaquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the seguimientoPaquete using partial update
        SeguimientoPaquete partialUpdatedSeguimientoPaquete = new SeguimientoPaquete();
        partialUpdatedSeguimientoPaquete.setId(seguimientoPaquete.getId());

        partialUpdatedSeguimientoPaquete.fecha(UPDATED_FECHA);

        restSeguimientoPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeguimientoPaquete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSeguimientoPaquete))
            )
            .andExpect(status().isOk());

        // Validate the SeguimientoPaquete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSeguimientoPaqueteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSeguimientoPaquete, seguimientoPaquete),
            getPersistedSeguimientoPaquete(seguimientoPaquete)
        );
    }

    @Test
    @Transactional
    void fullUpdateSeguimientoPaqueteWithPatch() throws Exception {
        // Initialize the database
        insertedSeguimientoPaquete = seguimientoPaqueteRepository.saveAndFlush(seguimientoPaquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the seguimientoPaquete using partial update
        SeguimientoPaquete partialUpdatedSeguimientoPaquete = new SeguimientoPaquete();
        partialUpdatedSeguimientoPaquete.setId(seguimientoPaquete.getId());

        partialUpdatedSeguimientoPaquete
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA)
            .ubicacion(UPDATED_UBICACION)
            .observaciones(UPDATED_OBSERVACIONES);

        restSeguimientoPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeguimientoPaquete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSeguimientoPaquete))
            )
            .andExpect(status().isOk());

        // Validate the SeguimientoPaquete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSeguimientoPaqueteUpdatableFieldsEquals(
            partialUpdatedSeguimientoPaquete,
            getPersistedSeguimientoPaquete(partialUpdatedSeguimientoPaquete)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seguimientoPaquete.setId(longCount.incrementAndGet());

        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguimientoPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seguimientoPaqueteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(seguimientoPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seguimientoPaquete.setId(longCount.incrementAndGet());

        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguimientoPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(seguimientoPaqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeguimientoPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        seguimientoPaquete.setId(longCount.incrementAndGet());

        // Create the SeguimientoPaquete
        SeguimientoPaqueteDTO seguimientoPaqueteDTO = seguimientoPaqueteMapper.toDto(seguimientoPaquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguimientoPaqueteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(seguimientoPaqueteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SeguimientoPaquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeguimientoPaquete() throws Exception {
        // Initialize the database
        insertedSeguimientoPaquete = seguimientoPaqueteRepository.saveAndFlush(seguimientoPaquete);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the seguimientoPaquete
        restSeguimientoPaqueteMockMvc
            .perform(delete(ENTITY_API_URL_ID, seguimientoPaquete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return seguimientoPaqueteRepository.count();
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

    protected SeguimientoPaquete getPersistedSeguimientoPaquete(SeguimientoPaquete seguimientoPaquete) {
        return seguimientoPaqueteRepository.findById(seguimientoPaquete.getId()).orElseThrow();
    }

    protected void assertPersistedSeguimientoPaqueteToMatchAllProperties(SeguimientoPaquete expectedSeguimientoPaquete) {
        assertSeguimientoPaqueteAllPropertiesEquals(expectedSeguimientoPaquete, getPersistedSeguimientoPaquete(expectedSeguimientoPaquete));
    }

    protected void assertPersistedSeguimientoPaqueteToMatchUpdatableProperties(SeguimientoPaquete expectedSeguimientoPaquete) {
        assertSeguimientoPaqueteAllUpdatablePropertiesEquals(
            expectedSeguimientoPaquete,
            getPersistedSeguimientoPaquete(expectedSeguimientoPaquete)
        );
    }
}
