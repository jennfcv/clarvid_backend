package com.paqueteria.web.rest;

import static com.paqueteria.domain.RepartidorAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.Repartidor;
import com.paqueteria.domain.User;
import com.paqueteria.repository.RepartidorRepository;
import com.paqueteria.repository.UserRepository;
import com.paqueteria.service.RepartidorService;
import com.paqueteria.service.dto.RepartidorDTO;
import com.paqueteria.service.mapper.RepartidorMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RepartidorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RepartidorResourceIT {

    private static final String DEFAULT_CI = "AAAAAAAAAA";
    private static final String UPDATED_CI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBLE = false;
    private static final Boolean UPDATED_DISPONIBLE = true;

    private static final Instant DEFAULT_FECHA_INGRESO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INGRESO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MEDIO_TRANSPORTE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIO_TRANSPORTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/repartidors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RepartidorRepository repartidorRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private RepartidorRepository repartidorRepositoryMock;

    @Autowired
    private RepartidorMapper repartidorMapper;

    @Mock
    private RepartidorService repartidorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepartidorMockMvc;

    private Repartidor repartidor;

    private Repartidor insertedRepartidor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repartidor createEntity(EntityManager em) {
        Repartidor repartidor = new Repartidor()
            .ci(DEFAULT_CI)
            .telefono(DEFAULT_TELEFONO)
            .direccion(DEFAULT_DIRECCION)
            .disponible(DEFAULT_DISPONIBLE)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .medioTransporte(DEFAULT_MEDIO_TRANSPORTE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        repartidor.setUsuario(user);
        return repartidor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repartidor createUpdatedEntity(EntityManager em) {
        Repartidor updatedRepartidor = new Repartidor()
            .ci(UPDATED_CI)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .disponible(UPDATED_DISPONIBLE)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .medioTransporte(UPDATED_MEDIO_TRANSPORTE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedRepartidor.setUsuario(user);
        return updatedRepartidor;
    }

    @BeforeEach
    void initTest() {
        repartidor = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedRepartidor != null) {
            repartidorRepository.delete(insertedRepartidor);
            insertedRepartidor = null;
        }
    }

    @Test
    @Transactional
    void createRepartidor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);
        var returnedRepartidorDTO = om.readValue(
            restRepartidorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(repartidorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RepartidorDTO.class
        );

        // Validate the Repartidor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRepartidor = repartidorMapper.toEntity(returnedRepartidorDTO);
        assertRepartidorUpdatableFieldsEquals(returnedRepartidor, getPersistedRepartidor(returnedRepartidor));

        insertedRepartidor = returnedRepartidor;
    }

    @Test
    @Transactional
    void createRepartidorWithExistingId() throws Exception {
        // Create the Repartidor with an existing ID
        repartidor.setId(1L);
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepartidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(repartidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCiIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        repartidor.setCi(null);

        // Create the Repartidor, which fails.
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        restRepartidorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(repartidorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRepartidors() throws Exception {
        // Initialize the database
        insertedRepartidor = repartidorRepository.saveAndFlush(repartidor);

        // Get all the repartidorList
        restRepartidorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repartidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].disponible").value(hasItem(DEFAULT_DISPONIBLE)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].medioTransporte").value(hasItem(DEFAULT_MEDIO_TRANSPORTE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRepartidorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(repartidorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRepartidorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(repartidorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRepartidorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(repartidorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRepartidorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(repartidorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRepartidor() throws Exception {
        // Initialize the database
        insertedRepartidor = repartidorRepository.saveAndFlush(repartidor);

        // Get the repartidor
        restRepartidorMockMvc
            .perform(get(ENTITY_API_URL_ID, repartidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(repartidor.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.disponible").value(DEFAULT_DISPONIBLE))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.medioTransporte").value(DEFAULT_MEDIO_TRANSPORTE));
    }

    @Test
    @Transactional
    void getNonExistingRepartidor() throws Exception {
        // Get the repartidor
        restRepartidorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRepartidor() throws Exception {
        // Initialize the database
        insertedRepartidor = repartidorRepository.saveAndFlush(repartidor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the repartidor
        Repartidor updatedRepartidor = repartidorRepository.findById(repartidor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRepartidor are not directly saved in db
        em.detach(updatedRepartidor);
        updatedRepartidor
            .ci(UPDATED_CI)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .disponible(UPDATED_DISPONIBLE)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .medioTransporte(UPDATED_MEDIO_TRANSPORTE);
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(updatedRepartidor);

        restRepartidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, repartidorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(repartidorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRepartidorToMatchAllProperties(updatedRepartidor);
    }

    @Test
    @Transactional
    void putNonExistingRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        repartidor.setId(longCount.incrementAndGet());

        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepartidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, repartidorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(repartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        repartidor.setId(longCount.incrementAndGet());

        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartidorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(repartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        repartidor.setId(longCount.incrementAndGet());

        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartidorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(repartidorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRepartidorWithPatch() throws Exception {
        // Initialize the database
        insertedRepartidor = repartidorRepository.saveAndFlush(repartidor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the repartidor using partial update
        Repartidor partialUpdatedRepartidor = new Repartidor();
        partialUpdatedRepartidor.setId(repartidor.getId());

        partialUpdatedRepartidor.direccion(UPDATED_DIRECCION).disponible(UPDATED_DISPONIBLE).fechaIngreso(UPDATED_FECHA_INGRESO);

        restRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepartidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRepartidor))
            )
            .andExpect(status().isOk());

        // Validate the Repartidor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRepartidorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRepartidor, repartidor),
            getPersistedRepartidor(repartidor)
        );
    }

    @Test
    @Transactional
    void fullUpdateRepartidorWithPatch() throws Exception {
        // Initialize the database
        insertedRepartidor = repartidorRepository.saveAndFlush(repartidor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the repartidor using partial update
        Repartidor partialUpdatedRepartidor = new Repartidor();
        partialUpdatedRepartidor.setId(repartidor.getId());

        partialUpdatedRepartidor
            .ci(UPDATED_CI)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .disponible(UPDATED_DISPONIBLE)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .medioTransporte(UPDATED_MEDIO_TRANSPORTE);

        restRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepartidor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRepartidor))
            )
            .andExpect(status().isOk());

        // Validate the Repartidor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRepartidorUpdatableFieldsEquals(partialUpdatedRepartidor, getPersistedRepartidor(partialUpdatedRepartidor));
    }

    @Test
    @Transactional
    void patchNonExistingRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        repartidor.setId(longCount.incrementAndGet());

        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, repartidorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(repartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        repartidor.setId(longCount.incrementAndGet());

        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartidorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(repartidorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRepartidor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        repartidor.setId(longCount.incrementAndGet());

        // Create the Repartidor
        RepartidorDTO repartidorDTO = repartidorMapper.toDto(repartidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepartidorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(repartidorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Repartidor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRepartidor() throws Exception {
        // Initialize the database
        insertedRepartidor = repartidorRepository.saveAndFlush(repartidor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the repartidor
        restRepartidorMockMvc
            .perform(delete(ENTITY_API_URL_ID, repartidor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return repartidorRepository.count();
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

    protected Repartidor getPersistedRepartidor(Repartidor repartidor) {
        return repartidorRepository.findById(repartidor.getId()).orElseThrow();
    }

    protected void assertPersistedRepartidorToMatchAllProperties(Repartidor expectedRepartidor) {
        assertRepartidorAllPropertiesEquals(expectedRepartidor, getPersistedRepartidor(expectedRepartidor));
    }

    protected void assertPersistedRepartidorToMatchUpdatableProperties(Repartidor expectedRepartidor) {
        assertRepartidorAllUpdatablePropertiesEquals(expectedRepartidor, getPersistedRepartidor(expectedRepartidor));
    }
}
