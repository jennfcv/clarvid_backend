package com.paqueteria.web.rest;

import static com.paqueteria.domain.RecepcionistaAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.Recepcionista;
import com.paqueteria.domain.User;
import com.paqueteria.repository.RecepcionistaRepository;
import com.paqueteria.repository.UserRepository;
import com.paqueteria.service.RecepcionistaService;
import com.paqueteria.service.dto.RecepcionistaDTO;
import com.paqueteria.service.mapper.RecepcionistaMapper;
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
 * Integration tests for the {@link RecepcionistaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RecepcionistaResourceIT {

    private static final String DEFAULT_CI = "AAAAAAAAAA";
    private static final String UPDATED_CI = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INGRESO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INGRESO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recepcionistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private RecepcionistaRepository recepcionistaRepositoryMock;

    @Autowired
    private RecepcionistaMapper recepcionistaMapper;

    @Mock
    private RecepcionistaService recepcionistaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecepcionistaMockMvc;

    private Recepcionista recepcionista;

    private Recepcionista insertedRecepcionista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recepcionista createEntity(EntityManager em) {
        Recepcionista recepcionista = new Recepcionista()
            .ci(DEFAULT_CI)
            .telefono(DEFAULT_TELEFONO)
            .direccion(DEFAULT_DIRECCION)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .observaciones(DEFAULT_OBSERVACIONES);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        recepcionista.setUsuario(user);
        return recepcionista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recepcionista createUpdatedEntity(EntityManager em) {
        Recepcionista updatedRecepcionista = new Recepcionista()
            .ci(UPDATED_CI)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .observaciones(UPDATED_OBSERVACIONES);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedRecepcionista.setUsuario(user);
        return updatedRecepcionista;
    }

    @BeforeEach
    void initTest() {
        recepcionista = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedRecepcionista != null) {
            recepcionistaRepository.delete(insertedRecepcionista);
            insertedRecepcionista = null;
        }
    }

    @Test
    @Transactional
    void createRecepcionista() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);
        var returnedRecepcionistaDTO = om.readValue(
            restRecepcionistaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recepcionistaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RecepcionistaDTO.class
        );

        // Validate the Recepcionista in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecepcionista = recepcionistaMapper.toEntity(returnedRecepcionistaDTO);
        assertRecepcionistaUpdatableFieldsEquals(returnedRecepcionista, getPersistedRecepcionista(returnedRecepcionista));

        insertedRecepcionista = returnedRecepcionista;
    }

    @Test
    @Transactional
    void createRecepcionistaWithExistingId() throws Exception {
        // Create the Recepcionista with an existing ID
        recepcionista.setId(1L);
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecepcionistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recepcionistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCiIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recepcionista.setCi(null);

        // Create the Recepcionista, which fails.
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        restRecepcionistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recepcionistaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecepcionistas() throws Exception {
        // Initialize the database
        insertedRecepcionista = recepcionistaRepository.saveAndFlush(recepcionista);

        // Get all the recepcionistaList
        restRecepcionistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recepcionista.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecepcionistasWithEagerRelationshipsIsEnabled() throws Exception {
        when(recepcionistaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecepcionistaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(recepcionistaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecepcionistasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(recepcionistaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecepcionistaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(recepcionistaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRecepcionista() throws Exception {
        // Initialize the database
        insertedRecepcionista = recepcionistaRepository.saveAndFlush(recepcionista);

        // Get the recepcionista
        restRecepcionistaMockMvc
            .perform(get(ENTITY_API_URL_ID, recepcionista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recepcionista.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }

    @Test
    @Transactional
    void getNonExistingRecepcionista() throws Exception {
        // Get the recepcionista
        restRecepcionistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecepcionista() throws Exception {
        // Initialize the database
        insertedRecepcionista = recepcionistaRepository.saveAndFlush(recepcionista);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recepcionista
        Recepcionista updatedRecepcionista = recepcionistaRepository.findById(recepcionista.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecepcionista are not directly saved in db
        em.detach(updatedRecepcionista);
        updatedRecepcionista
            .ci(UPDATED_CI)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .observaciones(UPDATED_OBSERVACIONES);
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(updatedRecepcionista);

        restRecepcionistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recepcionistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recepcionistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecepcionistaToMatchAllProperties(updatedRecepcionista);
    }

    @Test
    @Transactional
    void putNonExistingRecepcionista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recepcionista.setId(longCount.incrementAndGet());

        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecepcionistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recepcionistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recepcionistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecepcionista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recepcionista.setId(longCount.incrementAndGet());

        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecepcionistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recepcionistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecepcionista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recepcionista.setId(longCount.incrementAndGet());

        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecepcionistaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recepcionistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecepcionistaWithPatch() throws Exception {
        // Initialize the database
        insertedRecepcionista = recepcionistaRepository.saveAndFlush(recepcionista);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recepcionista using partial update
        Recepcionista partialUpdatedRecepcionista = new Recepcionista();
        partialUpdatedRecepcionista.setId(recepcionista.getId());

        partialUpdatedRecepcionista.ci(UPDATED_CI).fechaIngreso(UPDATED_FECHA_INGRESO);

        restRecepcionistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecepcionista.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecepcionista))
            )
            .andExpect(status().isOk());

        // Validate the Recepcionista in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecepcionistaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRecepcionista, recepcionista),
            getPersistedRecepcionista(recepcionista)
        );
    }

    @Test
    @Transactional
    void fullUpdateRecepcionistaWithPatch() throws Exception {
        // Initialize the database
        insertedRecepcionista = recepcionistaRepository.saveAndFlush(recepcionista);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recepcionista using partial update
        Recepcionista partialUpdatedRecepcionista = new Recepcionista();
        partialUpdatedRecepcionista.setId(recepcionista.getId());

        partialUpdatedRecepcionista
            .ci(UPDATED_CI)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .observaciones(UPDATED_OBSERVACIONES);

        restRecepcionistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecepcionista.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecepcionista))
            )
            .andExpect(status().isOk());

        // Validate the Recepcionista in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecepcionistaUpdatableFieldsEquals(partialUpdatedRecepcionista, getPersistedRecepcionista(partialUpdatedRecepcionista));
    }

    @Test
    @Transactional
    void patchNonExistingRecepcionista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recepcionista.setId(longCount.incrementAndGet());

        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecepcionistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recepcionistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recepcionistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecepcionista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recepcionista.setId(longCount.incrementAndGet());

        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecepcionistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recepcionistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecepcionista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recepcionista.setId(longCount.incrementAndGet());

        // Create the Recepcionista
        RecepcionistaDTO recepcionistaDTO = recepcionistaMapper.toDto(recepcionista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecepcionistaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recepcionistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recepcionista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecepcionista() throws Exception {
        // Initialize the database
        insertedRecepcionista = recepcionistaRepository.saveAndFlush(recepcionista);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recepcionista
        restRecepcionistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, recepcionista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recepcionistaRepository.count();
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

    protected Recepcionista getPersistedRecepcionista(Recepcionista recepcionista) {
        return recepcionistaRepository.findById(recepcionista.getId()).orElseThrow();
    }

    protected void assertPersistedRecepcionistaToMatchAllProperties(Recepcionista expectedRecepcionista) {
        assertRecepcionistaAllPropertiesEquals(expectedRecepcionista, getPersistedRecepcionista(expectedRecepcionista));
    }

    protected void assertPersistedRecepcionistaToMatchUpdatableProperties(Recepcionista expectedRecepcionista) {
        assertRecepcionistaAllUpdatablePropertiesEquals(expectedRecepcionista, getPersistedRecepcionista(expectedRecepcionista));
    }
}
