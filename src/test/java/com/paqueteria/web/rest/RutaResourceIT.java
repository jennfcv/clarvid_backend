package com.paqueteria.web.rest;

import static com.paqueteria.domain.RutaAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.Ruta;
import com.paqueteria.repository.RutaRepository;
import com.paqueteria.service.dto.RutaDTO;
import com.paqueteria.service.mapper.RutaMapper;
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
 * Integration tests for the {@link RutaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RutaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVA = false;
    private static final Boolean UPDATED_ACTIVA = true;

    private static final String ENTITY_API_URL = "/api/rutas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private RutaMapper rutaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRutaMockMvc;

    private Ruta ruta;

    private Ruta insertedRuta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ruta createEntity() {
        return new Ruta().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION).activa(DEFAULT_ACTIVA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ruta createUpdatedEntity() {
        return new Ruta().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).activa(UPDATED_ACTIVA);
    }

    @BeforeEach
    void initTest() {
        ruta = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedRuta != null) {
            rutaRepository.delete(insertedRuta);
            insertedRuta = null;
        }
    }

    @Test
    @Transactional
    void createRuta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);
        var returnedRutaDTO = om.readValue(
            restRutaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rutaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RutaDTO.class
        );

        // Validate the Ruta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRuta = rutaMapper.toEntity(returnedRutaDTO);
        assertRutaUpdatableFieldsEquals(returnedRuta, getPersistedRuta(returnedRuta));

        insertedRuta = returnedRuta;
    }

    @Test
    @Transactional
    void createRutaWithExistingId() throws Exception {
        // Create the Ruta with an existing ID
        ruta.setId(1L);
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRutaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rutaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ruta.setNombre(null);

        // Create the Ruta, which fails.
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        restRutaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rutaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRutas() throws Exception {
        // Initialize the database
        insertedRuta = rutaRepository.saveAndFlush(ruta);

        // Get all the rutaList
        restRutaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].activa").value(hasItem(DEFAULT_ACTIVA)));
    }

    @Test
    @Transactional
    void getRuta() throws Exception {
        // Initialize the database
        insertedRuta = rutaRepository.saveAndFlush(ruta);

        // Get the ruta
        restRutaMockMvc
            .perform(get(ENTITY_API_URL_ID, ruta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.activa").value(DEFAULT_ACTIVA));
    }

    @Test
    @Transactional
    void getNonExistingRuta() throws Exception {
        // Get the ruta
        restRutaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRuta() throws Exception {
        // Initialize the database
        insertedRuta = rutaRepository.saveAndFlush(ruta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ruta
        Ruta updatedRuta = rutaRepository.findById(ruta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRuta are not directly saved in db
        em.detach(updatedRuta);
        updatedRuta.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).activa(UPDATED_ACTIVA);
        RutaDTO rutaDTO = rutaMapper.toDto(updatedRuta);

        restRutaMockMvc
            .perform(put(ENTITY_API_URL_ID, rutaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rutaDTO)))
            .andExpect(status().isOk());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRutaToMatchAllProperties(updatedRuta);
    }

    @Test
    @Transactional
    void putNonExistingRuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ruta.setId(longCount.incrementAndGet());

        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRutaMockMvc
            .perform(put(ENTITY_API_URL_ID, rutaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rutaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ruta.setId(longCount.incrementAndGet());

        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRutaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rutaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ruta.setId(longCount.incrementAndGet());

        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRutaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rutaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRutaWithPatch() throws Exception {
        // Initialize the database
        insertedRuta = rutaRepository.saveAndFlush(ruta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ruta using partial update
        Ruta partialUpdatedRuta = new Ruta();
        partialUpdatedRuta.setId(ruta.getId());

        partialUpdatedRuta.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).activa(UPDATED_ACTIVA);

        restRutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRuta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRuta))
            )
            .andExpect(status().isOk());

        // Validate the Ruta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRutaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRuta, ruta), getPersistedRuta(ruta));
    }

    @Test
    @Transactional
    void fullUpdateRutaWithPatch() throws Exception {
        // Initialize the database
        insertedRuta = rutaRepository.saveAndFlush(ruta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ruta using partial update
        Ruta partialUpdatedRuta = new Ruta();
        partialUpdatedRuta.setId(ruta.getId());

        partialUpdatedRuta.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION).activa(UPDATED_ACTIVA);

        restRutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRuta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRuta))
            )
            .andExpect(status().isOk());

        // Validate the Ruta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRutaUpdatableFieldsEquals(partialUpdatedRuta, getPersistedRuta(partialUpdatedRuta));
    }

    @Test
    @Transactional
    void patchNonExistingRuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ruta.setId(longCount.incrementAndGet());

        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rutaDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rutaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ruta.setId(longCount.incrementAndGet());

        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRutaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rutaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRuta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ruta.setId(longCount.incrementAndGet());

        // Create the Ruta
        RutaDTO rutaDTO = rutaMapper.toDto(ruta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRutaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rutaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ruta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRuta() throws Exception {
        // Initialize the database
        insertedRuta = rutaRepository.saveAndFlush(ruta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ruta
        restRutaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ruta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rutaRepository.count();
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

    protected Ruta getPersistedRuta(Ruta ruta) {
        return rutaRepository.findById(ruta.getId()).orElseThrow();
    }

    protected void assertPersistedRutaToMatchAllProperties(Ruta expectedRuta) {
        assertRutaAllPropertiesEquals(expectedRuta, getPersistedRuta(expectedRuta));
    }

    protected void assertPersistedRutaToMatchUpdatableProperties(Ruta expectedRuta) {
        assertRutaAllUpdatablePropertiesEquals(expectedRuta, getPersistedRuta(expectedRuta));
    }
}
