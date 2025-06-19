package com.paqueteria.web.rest;

import static com.paqueteria.domain.IntentoEntregaAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.IntentoEntrega;
import com.paqueteria.repository.IntentoEntregaRepository;
import com.paqueteria.service.dto.IntentoEntregaDTO;
import com.paqueteria.service.mapper.IntentoEntregaMapper;
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
 * Integration tests for the {@link IntentoEntregaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntentoEntregaResourceIT {

    private static final Instant DEFAULT_FECHA_INTENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INTENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final Instant DEFAULT_PROXIMO_INTENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PROXIMO_INTENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/intento-entregas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IntentoEntregaRepository intentoEntregaRepository;

    @Autowired
    private IntentoEntregaMapper intentoEntregaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntentoEntregaMockMvc;

    private IntentoEntrega intentoEntrega;

    private IntentoEntrega insertedIntentoEntrega;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntentoEntrega createEntity() {
        return new IntentoEntrega()
            .fechaIntento(DEFAULT_FECHA_INTENTO)
            .resultado(DEFAULT_RESULTADO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .proximoIntento(DEFAULT_PROXIMO_INTENTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntentoEntrega createUpdatedEntity() {
        return new IntentoEntrega()
            .fechaIntento(UPDATED_FECHA_INTENTO)
            .resultado(UPDATED_RESULTADO)
            .observaciones(UPDATED_OBSERVACIONES)
            .proximoIntento(UPDATED_PROXIMO_INTENTO);
    }

    @BeforeEach
    void initTest() {
        intentoEntrega = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedIntentoEntrega != null) {
            intentoEntregaRepository.delete(insertedIntentoEntrega);
            insertedIntentoEntrega = null;
        }
    }

    @Test
    @Transactional
    void createIntentoEntrega() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);
        var returnedIntentoEntregaDTO = om.readValue(
            restIntentoEntregaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intentoEntregaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IntentoEntregaDTO.class
        );

        // Validate the IntentoEntrega in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIntentoEntrega = intentoEntregaMapper.toEntity(returnedIntentoEntregaDTO);
        assertIntentoEntregaUpdatableFieldsEquals(returnedIntentoEntrega, getPersistedIntentoEntrega(returnedIntentoEntrega));

        insertedIntentoEntrega = returnedIntentoEntrega;
    }

    @Test
    @Transactional
    void createIntentoEntregaWithExistingId() throws Exception {
        // Create the IntentoEntrega with an existing ID
        intentoEntrega.setId(1L);
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntentoEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intentoEntregaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIntentoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        intentoEntrega.setFechaIntento(null);

        // Create the IntentoEntrega, which fails.
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        restIntentoEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intentoEntregaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResultadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        intentoEntrega.setResultado(null);

        // Create the IntentoEntrega, which fails.
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        restIntentoEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intentoEntregaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIntentoEntregas() throws Exception {
        // Initialize the database
        insertedIntentoEntrega = intentoEntregaRepository.saveAndFlush(intentoEntrega);

        // Get all the intentoEntregaList
        restIntentoEntregaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intentoEntrega.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaIntento").value(hasItem(DEFAULT_FECHA_INTENTO.toString())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].proximoIntento").value(hasItem(DEFAULT_PROXIMO_INTENTO.toString())));
    }

    @Test
    @Transactional
    void getIntentoEntrega() throws Exception {
        // Initialize the database
        insertedIntentoEntrega = intentoEntregaRepository.saveAndFlush(intentoEntrega);

        // Get the intentoEntrega
        restIntentoEntregaMockMvc
            .perform(get(ENTITY_API_URL_ID, intentoEntrega.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intentoEntrega.getId().intValue()))
            .andExpect(jsonPath("$.fechaIntento").value(DEFAULT_FECHA_INTENTO.toString()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.proximoIntento").value(DEFAULT_PROXIMO_INTENTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIntentoEntrega() throws Exception {
        // Get the intentoEntrega
        restIntentoEntregaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntentoEntrega() throws Exception {
        // Initialize the database
        insertedIntentoEntrega = intentoEntregaRepository.saveAndFlush(intentoEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intentoEntrega
        IntentoEntrega updatedIntentoEntrega = intentoEntregaRepository.findById(intentoEntrega.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIntentoEntrega are not directly saved in db
        em.detach(updatedIntentoEntrega);
        updatedIntentoEntrega
            .fechaIntento(UPDATED_FECHA_INTENTO)
            .resultado(UPDATED_RESULTADO)
            .observaciones(UPDATED_OBSERVACIONES)
            .proximoIntento(UPDATED_PROXIMO_INTENTO);
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(updatedIntentoEntrega);

        restIntentoEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intentoEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intentoEntregaDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIntentoEntregaToMatchAllProperties(updatedIntentoEntrega);
    }

    @Test
    @Transactional
    void putNonExistingIntentoEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intentoEntrega.setId(longCount.incrementAndGet());

        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntentoEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intentoEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intentoEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntentoEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intentoEntrega.setId(longCount.incrementAndGet());

        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntentoEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intentoEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntentoEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intentoEntrega.setId(longCount.incrementAndGet());

        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntentoEntregaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intentoEntregaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntentoEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedIntentoEntrega = intentoEntregaRepository.saveAndFlush(intentoEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intentoEntrega using partial update
        IntentoEntrega partialUpdatedIntentoEntrega = new IntentoEntrega();
        partialUpdatedIntentoEntrega.setId(intentoEntrega.getId());

        partialUpdatedIntentoEntrega.fechaIntento(UPDATED_FECHA_INTENTO).observaciones(UPDATED_OBSERVACIONES);

        restIntentoEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntentoEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntentoEntrega))
            )
            .andExpect(status().isOk());

        // Validate the IntentoEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntentoEntregaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIntentoEntrega, intentoEntrega),
            getPersistedIntentoEntrega(intentoEntrega)
        );
    }

    @Test
    @Transactional
    void fullUpdateIntentoEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedIntentoEntrega = intentoEntregaRepository.saveAndFlush(intentoEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intentoEntrega using partial update
        IntentoEntrega partialUpdatedIntentoEntrega = new IntentoEntrega();
        partialUpdatedIntentoEntrega.setId(intentoEntrega.getId());

        partialUpdatedIntentoEntrega
            .fechaIntento(UPDATED_FECHA_INTENTO)
            .resultado(UPDATED_RESULTADO)
            .observaciones(UPDATED_OBSERVACIONES)
            .proximoIntento(UPDATED_PROXIMO_INTENTO);

        restIntentoEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntentoEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntentoEntrega))
            )
            .andExpect(status().isOk());

        // Validate the IntentoEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntentoEntregaUpdatableFieldsEquals(partialUpdatedIntentoEntrega, getPersistedIntentoEntrega(partialUpdatedIntentoEntrega));
    }

    @Test
    @Transactional
    void patchNonExistingIntentoEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intentoEntrega.setId(longCount.incrementAndGet());

        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntentoEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intentoEntregaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(intentoEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntentoEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intentoEntrega.setId(longCount.incrementAndGet());

        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntentoEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(intentoEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntentoEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intentoEntrega.setId(longCount.incrementAndGet());

        // Create the IntentoEntrega
        IntentoEntregaDTO intentoEntregaDTO = intentoEntregaMapper.toDto(intentoEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntentoEntregaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(intentoEntregaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntentoEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntentoEntrega() throws Exception {
        // Initialize the database
        insertedIntentoEntrega = intentoEntregaRepository.saveAndFlush(intentoEntrega);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the intentoEntrega
        restIntentoEntregaMockMvc
            .perform(delete(ENTITY_API_URL_ID, intentoEntrega.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return intentoEntregaRepository.count();
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

    protected IntentoEntrega getPersistedIntentoEntrega(IntentoEntrega intentoEntrega) {
        return intentoEntregaRepository.findById(intentoEntrega.getId()).orElseThrow();
    }

    protected void assertPersistedIntentoEntregaToMatchAllProperties(IntentoEntrega expectedIntentoEntrega) {
        assertIntentoEntregaAllPropertiesEquals(expectedIntentoEntrega, getPersistedIntentoEntrega(expectedIntentoEntrega));
    }

    protected void assertPersistedIntentoEntregaToMatchUpdatableProperties(IntentoEntrega expectedIntentoEntrega) {
        assertIntentoEntregaAllUpdatablePropertiesEquals(expectedIntentoEntrega, getPersistedIntentoEntrega(expectedIntentoEntrega));
    }
}
