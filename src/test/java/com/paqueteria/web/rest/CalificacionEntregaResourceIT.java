package com.paqueteria.web.rest;

import static com.paqueteria.domain.CalificacionEntregaAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.CalificacionEntrega;
import com.paqueteria.repository.CalificacionEntregaRepository;
import com.paqueteria.service.dto.CalificacionEntregaDTO;
import com.paqueteria.service.mapper.CalificacionEntregaMapper;
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
 * Integration tests for the {@link CalificacionEntregaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalificacionEntregaResourceIT {

    private static final Integer DEFAULT_PUNTAJE = 1;
    private static final Integer UPDATED_PUNTAJE = 2;

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CALIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CALIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/calificacion-entregas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CalificacionEntregaRepository calificacionEntregaRepository;

    @Autowired
    private CalificacionEntregaMapper calificacionEntregaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalificacionEntregaMockMvc;

    private CalificacionEntrega calificacionEntrega;

    private CalificacionEntrega insertedCalificacionEntrega;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalificacionEntrega createEntity() {
        return new CalificacionEntrega()
            .puntaje(DEFAULT_PUNTAJE)
            .comentario(DEFAULT_COMENTARIO)
            .fechaCalificacion(DEFAULT_FECHA_CALIFICACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalificacionEntrega createUpdatedEntity() {
        return new CalificacionEntrega()
            .puntaje(UPDATED_PUNTAJE)
            .comentario(UPDATED_COMENTARIO)
            .fechaCalificacion(UPDATED_FECHA_CALIFICACION);
    }

    @BeforeEach
    void initTest() {
        calificacionEntrega = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCalificacionEntrega != null) {
            calificacionEntregaRepository.delete(insertedCalificacionEntrega);
            insertedCalificacionEntrega = null;
        }
    }

    @Test
    @Transactional
    void createCalificacionEntrega() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);
        var returnedCalificacionEntregaDTO = om.readValue(
            restCalificacionEntregaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionEntregaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CalificacionEntregaDTO.class
        );

        // Validate the CalificacionEntrega in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCalificacionEntrega = calificacionEntregaMapper.toEntity(returnedCalificacionEntregaDTO);
        assertCalificacionEntregaUpdatableFieldsEquals(
            returnedCalificacionEntrega,
            getPersistedCalificacionEntrega(returnedCalificacionEntrega)
        );

        insertedCalificacionEntrega = returnedCalificacionEntrega;
    }

    @Test
    @Transactional
    void createCalificacionEntregaWithExistingId() throws Exception {
        // Create the CalificacionEntrega with an existing ID
        calificacionEntrega.setId(1L);
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalificacionEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionEntregaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCalificacionEntregas() throws Exception {
        // Initialize the database
        insertedCalificacionEntrega = calificacionEntregaRepository.saveAndFlush(calificacionEntrega);

        // Get all the calificacionEntregaList
        restCalificacionEntregaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calificacionEntrega.getId().intValue())))
            .andExpect(jsonPath("$.[*].puntaje").value(hasItem(DEFAULT_PUNTAJE)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fechaCalificacion").value(hasItem(DEFAULT_FECHA_CALIFICACION.toString())));
    }

    @Test
    @Transactional
    void getCalificacionEntrega() throws Exception {
        // Initialize the database
        insertedCalificacionEntrega = calificacionEntregaRepository.saveAndFlush(calificacionEntrega);

        // Get the calificacionEntrega
        restCalificacionEntregaMockMvc
            .perform(get(ENTITY_API_URL_ID, calificacionEntrega.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calificacionEntrega.getId().intValue()))
            .andExpect(jsonPath("$.puntaje").value(DEFAULT_PUNTAJE))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.fechaCalificacion").value(DEFAULT_FECHA_CALIFICACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCalificacionEntrega() throws Exception {
        // Get the calificacionEntrega
        restCalificacionEntregaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCalificacionEntrega() throws Exception {
        // Initialize the database
        insertedCalificacionEntrega = calificacionEntregaRepository.saveAndFlush(calificacionEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calificacionEntrega
        CalificacionEntrega updatedCalificacionEntrega = calificacionEntregaRepository.findById(calificacionEntrega.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCalificacionEntrega are not directly saved in db
        em.detach(updatedCalificacionEntrega);
        updatedCalificacionEntrega.puntaje(UPDATED_PUNTAJE).comentario(UPDATED_COMENTARIO).fechaCalificacion(UPDATED_FECHA_CALIFICACION);
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(updatedCalificacionEntrega);

        restCalificacionEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calificacionEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calificacionEntregaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCalificacionEntregaToMatchAllProperties(updatedCalificacionEntrega);
    }

    @Test
    @Transactional
    void putNonExistingCalificacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacionEntrega.setId(longCount.incrementAndGet());

        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalificacionEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calificacionEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calificacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalificacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacionEntrega.setId(longCount.incrementAndGet());

        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calificacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalificacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacionEntrega.setId(longCount.incrementAndGet());

        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionEntregaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calificacionEntregaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalificacionEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedCalificacionEntrega = calificacionEntregaRepository.saveAndFlush(calificacionEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calificacionEntrega using partial update
        CalificacionEntrega partialUpdatedCalificacionEntrega = new CalificacionEntrega();
        partialUpdatedCalificacionEntrega.setId(calificacionEntrega.getId());

        partialUpdatedCalificacionEntrega.puntaje(UPDATED_PUNTAJE).fechaCalificacion(UPDATED_FECHA_CALIFICACION);

        restCalificacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalificacionEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalificacionEntrega))
            )
            .andExpect(status().isOk());

        // Validate the CalificacionEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalificacionEntregaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCalificacionEntrega, calificacionEntrega),
            getPersistedCalificacionEntrega(calificacionEntrega)
        );
    }

    @Test
    @Transactional
    void fullUpdateCalificacionEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedCalificacionEntrega = calificacionEntregaRepository.saveAndFlush(calificacionEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calificacionEntrega using partial update
        CalificacionEntrega partialUpdatedCalificacionEntrega = new CalificacionEntrega();
        partialUpdatedCalificacionEntrega.setId(calificacionEntrega.getId());

        partialUpdatedCalificacionEntrega
            .puntaje(UPDATED_PUNTAJE)
            .comentario(UPDATED_COMENTARIO)
            .fechaCalificacion(UPDATED_FECHA_CALIFICACION);

        restCalificacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalificacionEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalificacionEntrega))
            )
            .andExpect(status().isOk());

        // Validate the CalificacionEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalificacionEntregaUpdatableFieldsEquals(
            partialUpdatedCalificacionEntrega,
            getPersistedCalificacionEntrega(partialUpdatedCalificacionEntrega)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCalificacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacionEntrega.setId(longCount.incrementAndGet());

        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalificacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calificacionEntregaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calificacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalificacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacionEntrega.setId(longCount.incrementAndGet());

        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calificacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalificacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calificacionEntrega.setId(longCount.incrementAndGet());

        // Create the CalificacionEntrega
        CalificacionEntregaDTO calificacionEntregaDTO = calificacionEntregaMapper.toDto(calificacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalificacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(calificacionEntregaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalificacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalificacionEntrega() throws Exception {
        // Initialize the database
        insertedCalificacionEntrega = calificacionEntregaRepository.saveAndFlush(calificacionEntrega);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the calificacionEntrega
        restCalificacionEntregaMockMvc
            .perform(delete(ENTITY_API_URL_ID, calificacionEntrega.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return calificacionEntregaRepository.count();
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

    protected CalificacionEntrega getPersistedCalificacionEntrega(CalificacionEntrega calificacionEntrega) {
        return calificacionEntregaRepository.findById(calificacionEntrega.getId()).orElseThrow();
    }

    protected void assertPersistedCalificacionEntregaToMatchAllProperties(CalificacionEntrega expectedCalificacionEntrega) {
        assertCalificacionEntregaAllPropertiesEquals(
            expectedCalificacionEntrega,
            getPersistedCalificacionEntrega(expectedCalificacionEntrega)
        );
    }

    protected void assertPersistedCalificacionEntregaToMatchUpdatableProperties(CalificacionEntrega expectedCalificacionEntrega) {
        assertCalificacionEntregaAllUpdatablePropertiesEquals(
            expectedCalificacionEntrega,
            getPersistedCalificacionEntrega(expectedCalificacionEntrega)
        );
    }
}
