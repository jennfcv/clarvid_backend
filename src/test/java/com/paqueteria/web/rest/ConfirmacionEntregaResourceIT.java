package com.paqueteria.web.rest;

import static com.paqueteria.domain.ConfirmacionEntregaAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.ConfirmacionEntrega;
import com.paqueteria.repository.ConfirmacionEntregaRepository;
import com.paqueteria.service.dto.ConfirmacionEntregaDTO;
import com.paqueteria.service.mapper.ConfirmacionEntregaMapper;
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
 * Integration tests for the {@link ConfirmacionEntregaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfirmacionEntregaResourceIT {

    private static final Instant DEFAULT_FECHA_CONFIRMACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CONFIRMACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOMBRE_RECEPTOR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_RECEPTOR = "BBBBBBBBBB";

    private static final String DEFAULT_CI_RECEPTOR = "AAAAAAAAAA";
    private static final String UPDATED_CI_RECEPTOR = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_FOTO_RECEPTOR = "AAAAAAAAAA";
    private static final String UPDATED_FOTO_RECEPTOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/confirmacion-entregas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConfirmacionEntregaRepository confirmacionEntregaRepository;

    @Autowired
    private ConfirmacionEntregaMapper confirmacionEntregaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfirmacionEntregaMockMvc;

    private ConfirmacionEntrega confirmacionEntrega;

    private ConfirmacionEntrega insertedConfirmacionEntrega;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfirmacionEntrega createEntity() {
        return new ConfirmacionEntrega()
            .fechaConfirmacion(DEFAULT_FECHA_CONFIRMACION)
            .nombreReceptor(DEFAULT_NOMBRE_RECEPTOR)
            .ciReceptor(DEFAULT_CI_RECEPTOR)
            .observaciones(DEFAULT_OBSERVACIONES)
            .fotoReceptor(DEFAULT_FOTO_RECEPTOR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConfirmacionEntrega createUpdatedEntity() {
        return new ConfirmacionEntrega()
            .fechaConfirmacion(UPDATED_FECHA_CONFIRMACION)
            .nombreReceptor(UPDATED_NOMBRE_RECEPTOR)
            .ciReceptor(UPDATED_CI_RECEPTOR)
            .observaciones(UPDATED_OBSERVACIONES)
            .fotoReceptor(UPDATED_FOTO_RECEPTOR);
    }

    @BeforeEach
    void initTest() {
        confirmacionEntrega = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedConfirmacionEntrega != null) {
            confirmacionEntregaRepository.delete(insertedConfirmacionEntrega);
            insertedConfirmacionEntrega = null;
        }
    }

    @Test
    @Transactional
    void createConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);
        var returnedConfirmacionEntregaDTO = om.readValue(
            restConfirmacionEntregaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmacionEntregaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConfirmacionEntregaDTO.class
        );

        // Validate the ConfirmacionEntrega in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConfirmacionEntrega = confirmacionEntregaMapper.toEntity(returnedConfirmacionEntregaDTO);
        assertConfirmacionEntregaUpdatableFieldsEquals(
            returnedConfirmacionEntrega,
            getPersistedConfirmacionEntrega(returnedConfirmacionEntrega)
        );

        insertedConfirmacionEntrega = returnedConfirmacionEntrega;
    }

    @Test
    @Transactional
    void createConfirmacionEntregaWithExistingId() throws Exception {
        // Create the ConfirmacionEntrega with an existing ID
        confirmacionEntrega.setId(1L);
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfirmacionEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmacionEntregaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaConfirmacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        confirmacionEntrega.setFechaConfirmacion(null);

        // Create the ConfirmacionEntrega, which fails.
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        restConfirmacionEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmacionEntregaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConfirmacionEntregas() throws Exception {
        // Initialize the database
        insertedConfirmacionEntrega = confirmacionEntregaRepository.saveAndFlush(confirmacionEntrega);

        // Get all the confirmacionEntregaList
        restConfirmacionEntregaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confirmacionEntrega.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaConfirmacion").value(hasItem(DEFAULT_FECHA_CONFIRMACION.toString())))
            .andExpect(jsonPath("$.[*].nombreReceptor").value(hasItem(DEFAULT_NOMBRE_RECEPTOR)))
            .andExpect(jsonPath("$.[*].ciReceptor").value(hasItem(DEFAULT_CI_RECEPTOR)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].fotoReceptor").value(hasItem(DEFAULT_FOTO_RECEPTOR)));
    }

    @Test
    @Transactional
    void getConfirmacionEntrega() throws Exception {
        // Initialize the database
        insertedConfirmacionEntrega = confirmacionEntregaRepository.saveAndFlush(confirmacionEntrega);

        // Get the confirmacionEntrega
        restConfirmacionEntregaMockMvc
            .perform(get(ENTITY_API_URL_ID, confirmacionEntrega.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(confirmacionEntrega.getId().intValue()))
            .andExpect(jsonPath("$.fechaConfirmacion").value(DEFAULT_FECHA_CONFIRMACION.toString()))
            .andExpect(jsonPath("$.nombreReceptor").value(DEFAULT_NOMBRE_RECEPTOR))
            .andExpect(jsonPath("$.ciReceptor").value(DEFAULT_CI_RECEPTOR))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.fotoReceptor").value(DEFAULT_FOTO_RECEPTOR));
    }

    @Test
    @Transactional
    void getNonExistingConfirmacionEntrega() throws Exception {
        // Get the confirmacionEntrega
        restConfirmacionEntregaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfirmacionEntrega() throws Exception {
        // Initialize the database
        insertedConfirmacionEntrega = confirmacionEntregaRepository.saveAndFlush(confirmacionEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the confirmacionEntrega
        ConfirmacionEntrega updatedConfirmacionEntrega = confirmacionEntregaRepository.findById(confirmacionEntrega.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConfirmacionEntrega are not directly saved in db
        em.detach(updatedConfirmacionEntrega);
        updatedConfirmacionEntrega
            .fechaConfirmacion(UPDATED_FECHA_CONFIRMACION)
            .nombreReceptor(UPDATED_NOMBRE_RECEPTOR)
            .ciReceptor(UPDATED_CI_RECEPTOR)
            .observaciones(UPDATED_OBSERVACIONES)
            .fotoReceptor(UPDATED_FOTO_RECEPTOR);
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(updatedConfirmacionEntrega);

        restConfirmacionEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, confirmacionEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(confirmacionEntregaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConfirmacionEntregaToMatchAllProperties(updatedConfirmacionEntrega);
    }

    @Test
    @Transactional
    void putNonExistingConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmacionEntrega.setId(longCount.incrementAndGet());

        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfirmacionEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, confirmacionEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(confirmacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmacionEntrega.setId(longCount.incrementAndGet());

        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmacionEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(confirmacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmacionEntrega.setId(longCount.incrementAndGet());

        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmacionEntregaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmacionEntregaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConfirmacionEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedConfirmacionEntrega = confirmacionEntregaRepository.saveAndFlush(confirmacionEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the confirmacionEntrega using partial update
        ConfirmacionEntrega partialUpdatedConfirmacionEntrega = new ConfirmacionEntrega();
        partialUpdatedConfirmacionEntrega.setId(confirmacionEntrega.getId());

        partialUpdatedConfirmacionEntrega
            .nombreReceptor(UPDATED_NOMBRE_RECEPTOR)
            .ciReceptor(UPDATED_CI_RECEPTOR)
            .observaciones(UPDATED_OBSERVACIONES);

        restConfirmacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfirmacionEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConfirmacionEntrega))
            )
            .andExpect(status().isOk());

        // Validate the ConfirmacionEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConfirmacionEntregaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConfirmacionEntrega, confirmacionEntrega),
            getPersistedConfirmacionEntrega(confirmacionEntrega)
        );
    }

    @Test
    @Transactional
    void fullUpdateConfirmacionEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedConfirmacionEntrega = confirmacionEntregaRepository.saveAndFlush(confirmacionEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the confirmacionEntrega using partial update
        ConfirmacionEntrega partialUpdatedConfirmacionEntrega = new ConfirmacionEntrega();
        partialUpdatedConfirmacionEntrega.setId(confirmacionEntrega.getId());

        partialUpdatedConfirmacionEntrega
            .fechaConfirmacion(UPDATED_FECHA_CONFIRMACION)
            .nombreReceptor(UPDATED_NOMBRE_RECEPTOR)
            .ciReceptor(UPDATED_CI_RECEPTOR)
            .observaciones(UPDATED_OBSERVACIONES)
            .fotoReceptor(UPDATED_FOTO_RECEPTOR);

        restConfirmacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfirmacionEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConfirmacionEntrega))
            )
            .andExpect(status().isOk());

        // Validate the ConfirmacionEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConfirmacionEntregaUpdatableFieldsEquals(
            partialUpdatedConfirmacionEntrega,
            getPersistedConfirmacionEntrega(partialUpdatedConfirmacionEntrega)
        );
    }

    @Test
    @Transactional
    void patchNonExistingConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmacionEntrega.setId(longCount.incrementAndGet());

        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfirmacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, confirmacionEntregaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(confirmacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmacionEntrega.setId(longCount.incrementAndGet());

        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(confirmacionEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfirmacionEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmacionEntrega.setId(longCount.incrementAndGet());

        // Create the ConfirmacionEntrega
        ConfirmacionEntregaDTO confirmacionEntregaDTO = confirmacionEntregaMapper.toDto(confirmacionEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmacionEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(confirmacionEntregaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConfirmacionEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConfirmacionEntrega() throws Exception {
        // Initialize the database
        insertedConfirmacionEntrega = confirmacionEntregaRepository.saveAndFlush(confirmacionEntrega);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the confirmacionEntrega
        restConfirmacionEntregaMockMvc
            .perform(delete(ENTITY_API_URL_ID, confirmacionEntrega.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return confirmacionEntregaRepository.count();
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

    protected ConfirmacionEntrega getPersistedConfirmacionEntrega(ConfirmacionEntrega confirmacionEntrega) {
        return confirmacionEntregaRepository.findById(confirmacionEntrega.getId()).orElseThrow();
    }

    protected void assertPersistedConfirmacionEntregaToMatchAllProperties(ConfirmacionEntrega expectedConfirmacionEntrega) {
        assertConfirmacionEntregaAllPropertiesEquals(
            expectedConfirmacionEntrega,
            getPersistedConfirmacionEntrega(expectedConfirmacionEntrega)
        );
    }

    protected void assertPersistedConfirmacionEntregaToMatchUpdatableProperties(ConfirmacionEntrega expectedConfirmacionEntrega) {
        assertConfirmacionEntregaAllUpdatablePropertiesEquals(
            expectedConfirmacionEntrega,
            getPersistedConfirmacionEntrega(expectedConfirmacionEntrega)
        );
    }
}
