package com.paqueteria.web.rest;

import static com.paqueteria.domain.ZonaEntregaAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.ZonaEntrega;
import com.paqueteria.repository.ZonaEntregaRepository;
import com.paqueteria.service.dto.ZonaEntregaDTO;
import com.paqueteria.service.mapper.ZonaEntregaMapper;
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
 * Integration tests for the {@link ZonaEntregaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ZonaEntregaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/zona-entregas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ZonaEntregaRepository zonaEntregaRepository;

    @Autowired
    private ZonaEntregaMapper zonaEntregaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restZonaEntregaMockMvc;

    private ZonaEntrega zonaEntrega;

    private ZonaEntrega insertedZonaEntrega;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZonaEntrega createEntity() {
        return new ZonaEntrega().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZonaEntrega createUpdatedEntity() {
        return new ZonaEntrega().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    void initTest() {
        zonaEntrega = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedZonaEntrega != null) {
            zonaEntregaRepository.delete(insertedZonaEntrega);
            insertedZonaEntrega = null;
        }
    }

    @Test
    @Transactional
    void createZonaEntrega() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);
        var returnedZonaEntregaDTO = om.readValue(
            restZonaEntregaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zonaEntregaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ZonaEntregaDTO.class
        );

        // Validate the ZonaEntrega in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedZonaEntrega = zonaEntregaMapper.toEntity(returnedZonaEntregaDTO);
        assertZonaEntregaUpdatableFieldsEquals(returnedZonaEntrega, getPersistedZonaEntrega(returnedZonaEntrega));

        insertedZonaEntrega = returnedZonaEntrega;
    }

    @Test
    @Transactional
    void createZonaEntregaWithExistingId() throws Exception {
        // Create the ZonaEntrega with an existing ID
        zonaEntrega.setId(1L);
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restZonaEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zonaEntregaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        zonaEntrega.setNombre(null);

        // Create the ZonaEntrega, which fails.
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        restZonaEntregaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zonaEntregaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllZonaEntregas() throws Exception {
        // Initialize the database
        insertedZonaEntrega = zonaEntregaRepository.saveAndFlush(zonaEntrega);

        // Get all the zonaEntregaList
        restZonaEntregaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zonaEntrega.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getZonaEntrega() throws Exception {
        // Initialize the database
        insertedZonaEntrega = zonaEntregaRepository.saveAndFlush(zonaEntrega);

        // Get the zonaEntrega
        restZonaEntregaMockMvc
            .perform(get(ENTITY_API_URL_ID, zonaEntrega.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(zonaEntrega.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingZonaEntrega() throws Exception {
        // Get the zonaEntrega
        restZonaEntregaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingZonaEntrega() throws Exception {
        // Initialize the database
        insertedZonaEntrega = zonaEntregaRepository.saveAndFlush(zonaEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zonaEntrega
        ZonaEntrega updatedZonaEntrega = zonaEntregaRepository.findById(zonaEntrega.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedZonaEntrega are not directly saved in db
        em.detach(updatedZonaEntrega);
        updatedZonaEntrega.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(updatedZonaEntrega);

        restZonaEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zonaEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zonaEntregaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedZonaEntregaToMatchAllProperties(updatedZonaEntrega);
    }

    @Test
    @Transactional
    void putNonExistingZonaEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zonaEntrega.setId(longCount.incrementAndGet());

        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZonaEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, zonaEntregaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zonaEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchZonaEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zonaEntrega.setId(longCount.incrementAndGet());

        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZonaEntregaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(zonaEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamZonaEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zonaEntrega.setId(longCount.incrementAndGet());

        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZonaEntregaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(zonaEntregaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateZonaEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedZonaEntrega = zonaEntregaRepository.saveAndFlush(zonaEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zonaEntrega using partial update
        ZonaEntrega partialUpdatedZonaEntrega = new ZonaEntrega();
        partialUpdatedZonaEntrega.setId(zonaEntrega.getId());

        partialUpdatedZonaEntrega.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restZonaEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZonaEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZonaEntrega))
            )
            .andExpect(status().isOk());

        // Validate the ZonaEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZonaEntregaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedZonaEntrega, zonaEntrega),
            getPersistedZonaEntrega(zonaEntrega)
        );
    }

    @Test
    @Transactional
    void fullUpdateZonaEntregaWithPatch() throws Exception {
        // Initialize the database
        insertedZonaEntrega = zonaEntregaRepository.saveAndFlush(zonaEntrega);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the zonaEntrega using partial update
        ZonaEntrega partialUpdatedZonaEntrega = new ZonaEntrega();
        partialUpdatedZonaEntrega.setId(zonaEntrega.getId());

        partialUpdatedZonaEntrega.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restZonaEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedZonaEntrega.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedZonaEntrega))
            )
            .andExpect(status().isOk());

        // Validate the ZonaEntrega in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertZonaEntregaUpdatableFieldsEquals(partialUpdatedZonaEntrega, getPersistedZonaEntrega(partialUpdatedZonaEntrega));
    }

    @Test
    @Transactional
    void patchNonExistingZonaEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zonaEntrega.setId(longCount.incrementAndGet());

        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZonaEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, zonaEntregaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zonaEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchZonaEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zonaEntrega.setId(longCount.incrementAndGet());

        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZonaEntregaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(zonaEntregaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamZonaEntrega() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        zonaEntrega.setId(longCount.incrementAndGet());

        // Create the ZonaEntrega
        ZonaEntregaDTO zonaEntregaDTO = zonaEntregaMapper.toDto(zonaEntrega);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restZonaEntregaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(zonaEntregaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ZonaEntrega in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteZonaEntrega() throws Exception {
        // Initialize the database
        insertedZonaEntrega = zonaEntregaRepository.saveAndFlush(zonaEntrega);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the zonaEntrega
        restZonaEntregaMockMvc
            .perform(delete(ENTITY_API_URL_ID, zonaEntrega.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return zonaEntregaRepository.count();
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

    protected ZonaEntrega getPersistedZonaEntrega(ZonaEntrega zonaEntrega) {
        return zonaEntregaRepository.findById(zonaEntrega.getId()).orElseThrow();
    }

    protected void assertPersistedZonaEntregaToMatchAllProperties(ZonaEntrega expectedZonaEntrega) {
        assertZonaEntregaAllPropertiesEquals(expectedZonaEntrega, getPersistedZonaEntrega(expectedZonaEntrega));
    }

    protected void assertPersistedZonaEntregaToMatchUpdatableProperties(ZonaEntrega expectedZonaEntrega) {
        assertZonaEntregaAllUpdatablePropertiesEquals(expectedZonaEntrega, getPersistedZonaEntrega(expectedZonaEntrega));
    }
}
