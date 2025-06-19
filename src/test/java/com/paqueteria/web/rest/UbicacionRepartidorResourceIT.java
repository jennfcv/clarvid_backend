package com.paqueteria.web.rest;

import static com.paqueteria.domain.UbicacionRepartidorAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.UbicacionRepartidor;
import com.paqueteria.repository.UbicacionRepartidorRepository;
import com.paqueteria.service.dto.UbicacionRepartidorDTO;
import com.paqueteria.service.mapper.UbicacionRepartidorMapper;
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
 * Integration tests for the {@link UbicacionRepartidorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UbicacionRepartidorResourceIT {

    private static final Double DEFAULT_LATITUD = 1D;
    private static final Double UPDATED_LATITUD = 2D;

    private static final Double DEFAULT_LONGITUD = 1D;
    private static final Double UPDATED_LONGITUD = 2D;

    private static final Instant DEFAULT_FECHA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ubicacion-repartidors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UbicacionRepartidorRepository ubicacionRepartidorRepository;

    @Autowired
    private UbicacionRepartidorMapper ubicacionRepartidorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUbicacionRepartidorMockMvc;

    private UbicacionRepartidor ubicacionRepartidor;

    private UbicacionRepartidor insertedUbicacionRepartidor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UbicacionRepartidor createEntity() {
        return new UbicacionRepartidor().latitud(DEFAULT_LATITUD).longitud(DEFAULT_LONGITUD).fechaHora(DEFAULT_FECHA_HORA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UbicacionRepartidor createUpdatedEntity() {
        return new UbicacionRepartidor().latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD).fechaHora(UPDATED_FECHA_HORA);
    }

    @BeforeEach
    void initTest() {
        ubicacionRepartidor = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedUbicacionRepartidor != null) {
            ubicacionRepartidorRepository.delete(insertedUbicacionRepartidor);
            insertedUbicacionRepartidor = null;
        }
    }

    @Test
    @Transactional
    void createUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);
        var returnedUbicacionRepartidorDTO = om.readValue(
            restUbicacionRepartidorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionRepartidorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UbicacionRepartidorDTO.class
        );

        // Validate the UbicacionRepartidor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUbicacionRepartidor = ubicacionRepartidorMapper.toEntity(returnedUbicacionRepartidorDTO);
        assertUbicacionRepartidorUpdatableFieldsEquals(
            returnedUbicacionRepartidor,
            getPersistedUbicacionRepartidor(returnedUbicacionRepartidor)
        );

        insertedUbicacionRepartidor = returnedUbicacionRepartidor;
    }

    @Test
    @Transactional
    void createUbicacionRepartidorWithExistingId() throws Exception {
        // Create the UbicacionRepartidor with an existing ID
        ubicacionRepartidor.setId(1L);
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUbicacionRepartidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionRepartidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLatitudIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ubicacionRepartidor.setLatitud(null);

        // Create the UbicacionRepartidor, which fails.
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        restUbicacionRepartidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionRepartidorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ubicacionRepartidor.setLongitud(null);

        // Create the UbicacionRepartidor, which fails.
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        restUbicacionRepartidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionRepartidorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaHoraIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ubicacionRepartidor.setFechaHora(null);

        // Create the UbicacionRepartidor, which fails.
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        restUbicacionRepartidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionRepartidorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUbicacionRepartidors() throws Exception {
        // Initialize the database
        insertedUbicacionRepartidor = ubicacionRepartidorRepository.saveAndFlush(ubicacionRepartidor);

        // Get all the ubicacionRepartidorList
        restUbicacionRepartidorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ubicacionRepartidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(DEFAULT_FECHA_HORA.toString())));
    }

    @Test
    @Transactional
    void getUbicacionRepartidor() throws Exception {
        // Initialize the database
        insertedUbicacionRepartidor = ubicacionRepartidorRepository.saveAndFlush(ubicacionRepartidor);

        // Get the ubicacionRepartidor
        restUbicacionRepartidorMockMvc
            .perform(get(ENTITY_API_URL_ID, ubicacionRepartidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ubicacionRepartidor.getId().intValue()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.fechaHora").value(DEFAULT_FECHA_HORA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUbicacionRepartidor() throws Exception {
        // Get the ubicacionRepartidor
        restUbicacionRepartidorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUbicacionRepartidor() throws Exception {
        // Initialize the database
        insertedUbicacionRepartidor = ubicacionRepartidorRepository.saveAndFlush(ubicacionRepartidor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ubicacionRepartidor
        UbicacionRepartidor updatedUbicacionRepartidor = ubicacionRepartidorRepository.findById(ubicacionRepartidor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUbicacionRepartidor are not directly saved in db
        em.detach(updatedUbicacionRepartidor);
        updatedUbicacionRepartidor.latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD).fechaHora(UPDATED_FECHA_HORA);
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(updatedUbicacionRepartidor);

        restUbicacionRepartidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ubicacionRepartidorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ubicacionRepartidorDTO))
            )
            .andExpect(status().isOk());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUbicacionRepartidorToMatchAllProperties(updatedUbicacionRepartidor);
    }

    @Test
    @Transactional
    void putNonExistingUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacionRepartidor.setId(longCount.incrementAndGet());

        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacionRepartidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ubicacionRepartidorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ubicacionRepartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacionRepartidor.setId(longCount.incrementAndGet());

        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionRepartidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ubicacionRepartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacionRepartidor.setId(longCount.incrementAndGet());

        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionRepartidorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ubicacionRepartidorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUbicacionRepartidorWithPatch() throws Exception {
        // Initialize the database
        insertedUbicacionRepartidor = ubicacionRepartidorRepository.saveAndFlush(ubicacionRepartidor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ubicacionRepartidor using partial update
        UbicacionRepartidor partialUpdatedUbicacionRepartidor = new UbicacionRepartidor();
        partialUpdatedUbicacionRepartidor.setId(ubicacionRepartidor.getId());

        partialUpdatedUbicacionRepartidor.latitud(UPDATED_LATITUD);

        restUbicacionRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUbicacionRepartidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUbicacionRepartidor))
            )
            .andExpect(status().isOk());

        // Validate the UbicacionRepartidor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUbicacionRepartidorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUbicacionRepartidor, ubicacionRepartidor),
            getPersistedUbicacionRepartidor(ubicacionRepartidor)
        );
    }

    @Test
    @Transactional
    void fullUpdateUbicacionRepartidorWithPatch() throws Exception {
        // Initialize the database
        insertedUbicacionRepartidor = ubicacionRepartidorRepository.saveAndFlush(ubicacionRepartidor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ubicacionRepartidor using partial update
        UbicacionRepartidor partialUpdatedUbicacionRepartidor = new UbicacionRepartidor();
        partialUpdatedUbicacionRepartidor.setId(ubicacionRepartidor.getId());

        partialUpdatedUbicacionRepartidor.latitud(UPDATED_LATITUD).longitud(UPDATED_LONGITUD).fechaHora(UPDATED_FECHA_HORA);

        restUbicacionRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUbicacionRepartidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUbicacionRepartidor))
            )
            .andExpect(status().isOk());

        // Validate the UbicacionRepartidor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUbicacionRepartidorUpdatableFieldsEquals(
            partialUpdatedUbicacionRepartidor,
            getPersistedUbicacionRepartidor(partialUpdatedUbicacionRepartidor)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacionRepartidor.setId(longCount.incrementAndGet());

        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacionRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ubicacionRepartidorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ubicacionRepartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacionRepartidor.setId(longCount.incrementAndGet());

        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ubicacionRepartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUbicacionRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ubicacionRepartidor.setId(longCount.incrementAndGet());

        // Create the UbicacionRepartidor
        UbicacionRepartidorDTO ubicacionRepartidorDTO = ubicacionRepartidorMapper.toDto(ubicacionRepartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ubicacionRepartidorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UbicacionRepartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUbicacionRepartidor() throws Exception {
        // Initialize the database
        insertedUbicacionRepartidor = ubicacionRepartidorRepository.saveAndFlush(ubicacionRepartidor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ubicacionRepartidor
        restUbicacionRepartidorMockMvc
            .perform(delete(ENTITY_API_URL_ID, ubicacionRepartidor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ubicacionRepartidorRepository.count();
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

    protected UbicacionRepartidor getPersistedUbicacionRepartidor(UbicacionRepartidor ubicacionRepartidor) {
        return ubicacionRepartidorRepository.findById(ubicacionRepartidor.getId()).orElseThrow();
    }

    protected void assertPersistedUbicacionRepartidorToMatchAllProperties(UbicacionRepartidor expectedUbicacionRepartidor) {
        assertUbicacionRepartidorAllPropertiesEquals(
            expectedUbicacionRepartidor,
            getPersistedUbicacionRepartidor(expectedUbicacionRepartidor)
        );
    }

    protected void assertPersistedUbicacionRepartidorToMatchUpdatableProperties(UbicacionRepartidor expectedUbicacionRepartidor) {
        assertUbicacionRepartidorAllUpdatablePropertiesEquals(
            expectedUbicacionRepartidor,
            getPersistedUbicacionRepartidor(expectedUbicacionRepartidor)
        );
    }
}
