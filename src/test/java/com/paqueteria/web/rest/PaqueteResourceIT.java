package com.paqueteria.web.rest;

import static com.paqueteria.domain.PaqueteAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.Paquete;
import com.paqueteria.domain.enumeration.EstadoPaquete;
import com.paqueteria.domain.enumeration.TipoEntrega;
import com.paqueteria.repository.PaqueteRepository;
import com.paqueteria.repository.UserRepository;
import com.paqueteria.service.PaqueteService;
import com.paqueteria.service.dto.PaqueteDTO;
import com.paqueteria.service.mapper.PaqueteMapper;
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
 * Integration tests for the {@link PaqueteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PaqueteResourceIT {

    private static final String DEFAULT_CODIGO_SEGUIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_SEGUIMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_DETALLE = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final Instant DEFAULT_FECHA_ENVIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ENVIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_ENTREGA_ESTIMADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ENTREGA_ESTIMADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EstadoPaquete DEFAULT_ESTADO = EstadoPaquete.REGISTRADO;
    private static final EstadoPaquete UPDATED_ESTADO = EstadoPaquete.EN_CAMINO;

    private static final TipoEntrega DEFAULT_TIPO_ENTREGA = TipoEntrega.RETIRO_SUCURSAL;
    private static final TipoEntrega UPDATED_TIPO_ENTREGA = TipoEntrega.ENTREGA_DIRECTA;

    private static final String DEFAULT_DIRECCION_ENTREGA = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_ENTREGA = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUD_ENTREGA = 1D;
    private static final Double UPDATED_LATITUD_ENTREGA = 2D;

    private static final Double DEFAULT_LONGITUD_ENTREGA = 1D;
    private static final Double UPDATED_LONGITUD_ENTREGA = 2D;

    private static final Boolean DEFAULT_CONFIRMADO = false;
    private static final Boolean UPDATED_CONFIRMADO = true;

    private static final String DEFAULT_CODIGO_QR = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_QR = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_ACTUAL = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_ACTUAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FRAGIL = false;
    private static final Boolean UPDATED_FRAGIL = true;

    private static final String DEFAULT_CLIENTE_TOKEN_ACCESO = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE_TOKEN_ACCESO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paquetes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaqueteRepository paqueteRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private PaqueteRepository paqueteRepositoryMock;

    @Autowired
    private PaqueteMapper paqueteMapper;

    @Mock
    private PaqueteService paqueteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaqueteMockMvc;

    private Paquete paquete;

    private Paquete insertedPaquete;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paquete createEntity() {
        return new Paquete()
            .codigoSeguimiento(DEFAULT_CODIGO_SEGUIMIENTO)
            .descripcion(DEFAULT_DESCRIPCION)
            .detalle(DEFAULT_DETALLE)
            .peso(DEFAULT_PESO)
            .fechaEnvio(DEFAULT_FECHA_ENVIO)
            .fechaEntregaEstimada(DEFAULT_FECHA_ENTREGA_ESTIMADA)
            .estado(DEFAULT_ESTADO)
            .tipoEntrega(DEFAULT_TIPO_ENTREGA)
            .direccionEntrega(DEFAULT_DIRECCION_ENTREGA)
            .latitudEntrega(DEFAULT_LATITUD_ENTREGA)
            .longitudEntrega(DEFAULT_LONGITUD_ENTREGA)
            .confirmado(DEFAULT_CONFIRMADO)
            .codigoQR(DEFAULT_CODIGO_QR)
            .ubicacionActual(DEFAULT_UBICACION_ACTUAL)
            .fragil(DEFAULT_FRAGIL)
            .clienteTokenAcceso(DEFAULT_CLIENTE_TOKEN_ACCESO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paquete createUpdatedEntity() {
        return new Paquete()
            .codigoSeguimiento(UPDATED_CODIGO_SEGUIMIENTO)
            .descripcion(UPDATED_DESCRIPCION)
            .detalle(UPDATED_DETALLE)
            .peso(UPDATED_PESO)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .fechaEntregaEstimada(UPDATED_FECHA_ENTREGA_ESTIMADA)
            .estado(UPDATED_ESTADO)
            .tipoEntrega(UPDATED_TIPO_ENTREGA)
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .latitudEntrega(UPDATED_LATITUD_ENTREGA)
            .longitudEntrega(UPDATED_LONGITUD_ENTREGA)
            .confirmado(UPDATED_CONFIRMADO)
            .codigoQR(UPDATED_CODIGO_QR)
            .ubicacionActual(UPDATED_UBICACION_ACTUAL)
            .fragil(UPDATED_FRAGIL)
            .clienteTokenAcceso(UPDATED_CLIENTE_TOKEN_ACCESO);
    }

    @BeforeEach
    void initTest() {
        paquete = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPaquete != null) {
            paqueteRepository.delete(insertedPaquete);
            insertedPaquete = null;
        }
    }

    @Test
    @Transactional
    void createPaquete() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);
        var returnedPaqueteDTO = om.readValue(
            restPaqueteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaqueteDTO.class
        );

        // Validate the Paquete in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPaquete = paqueteMapper.toEntity(returnedPaqueteDTO);
        assertPaqueteUpdatableFieldsEquals(returnedPaquete, getPersistedPaquete(returnedPaquete));

        insertedPaquete = returnedPaquete;
    }

    @Test
    @Transactional
    void createPaqueteWithExistingId() throws Exception {
        // Create the Paquete with an existing ID
        paquete.setId(1L);
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoSeguimientoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paquete.setCodigoSeguimiento(null);

        // Create the Paquete, which fails.
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        restPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paquete.setPeso(null);

        // Create the Paquete, which fails.
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        restPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaEnvioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paquete.setFechaEnvio(null);

        // Create the Paquete, which fails.
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        restPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paquete.setEstado(null);

        // Create the Paquete, which fails.
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        restPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoEntregaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        paquete.setTipoEntrega(null);

        // Create the Paquete, which fails.
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        restPaqueteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaquetes() throws Exception {
        // Initialize the database
        insertedPaquete = paqueteRepository.saveAndFlush(paquete);

        // Get all the paqueteList
        restPaqueteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paquete.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoSeguimiento").value(hasItem(DEFAULT_CODIGO_SEGUIMIENTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].detalle").value(hasItem(DEFAULT_DETALLE)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)))
            .andExpect(jsonPath("$.[*].fechaEnvio").value(hasItem(DEFAULT_FECHA_ENVIO.toString())))
            .andExpect(jsonPath("$.[*].fechaEntregaEstimada").value(hasItem(DEFAULT_FECHA_ENTREGA_ESTIMADA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].tipoEntrega").value(hasItem(DEFAULT_TIPO_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].direccionEntrega").value(hasItem(DEFAULT_DIRECCION_ENTREGA)))
            .andExpect(jsonPath("$.[*].latitudEntrega").value(hasItem(DEFAULT_LATITUD_ENTREGA)))
            .andExpect(jsonPath("$.[*].longitudEntrega").value(hasItem(DEFAULT_LONGITUD_ENTREGA)))
            .andExpect(jsonPath("$.[*].confirmado").value(hasItem(DEFAULT_CONFIRMADO)))
            .andExpect(jsonPath("$.[*].codigoQR").value(hasItem(DEFAULT_CODIGO_QR)))
            .andExpect(jsonPath("$.[*].ubicacionActual").value(hasItem(DEFAULT_UBICACION_ACTUAL)))
            .andExpect(jsonPath("$.[*].fragil").value(hasItem(DEFAULT_FRAGIL)))
            .andExpect(jsonPath("$.[*].clienteTokenAcceso").value(hasItem(DEFAULT_CLIENTE_TOKEN_ACCESO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaquetesWithEagerRelationshipsIsEnabled() throws Exception {
        when(paqueteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaqueteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(paqueteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaquetesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paqueteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaqueteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(paqueteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPaquete() throws Exception {
        // Initialize the database
        insertedPaquete = paqueteRepository.saveAndFlush(paquete);

        // Get the paquete
        restPaqueteMockMvc
            .perform(get(ENTITY_API_URL_ID, paquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paquete.getId().intValue()))
            .andExpect(jsonPath("$.codigoSeguimiento").value(DEFAULT_CODIGO_SEGUIMIENTO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.detalle").value(DEFAULT_DETALLE))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO))
            .andExpect(jsonPath("$.fechaEnvio").value(DEFAULT_FECHA_ENVIO.toString()))
            .andExpect(jsonPath("$.fechaEntregaEstimada").value(DEFAULT_FECHA_ENTREGA_ESTIMADA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.tipoEntrega").value(DEFAULT_TIPO_ENTREGA.toString()))
            .andExpect(jsonPath("$.direccionEntrega").value(DEFAULT_DIRECCION_ENTREGA))
            .andExpect(jsonPath("$.latitudEntrega").value(DEFAULT_LATITUD_ENTREGA))
            .andExpect(jsonPath("$.longitudEntrega").value(DEFAULT_LONGITUD_ENTREGA))
            .andExpect(jsonPath("$.confirmado").value(DEFAULT_CONFIRMADO))
            .andExpect(jsonPath("$.codigoQR").value(DEFAULT_CODIGO_QR))
            .andExpect(jsonPath("$.ubicacionActual").value(DEFAULT_UBICACION_ACTUAL))
            .andExpect(jsonPath("$.fragil").value(DEFAULT_FRAGIL))
            .andExpect(jsonPath("$.clienteTokenAcceso").value(DEFAULT_CLIENTE_TOKEN_ACCESO));
    }

    @Test
    @Transactional
    void getNonExistingPaquete() throws Exception {
        // Get the paquete
        restPaqueteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaquete() throws Exception {
        // Initialize the database
        insertedPaquete = paqueteRepository.saveAndFlush(paquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paquete
        Paquete updatedPaquete = paqueteRepository.findById(paquete.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaquete are not directly saved in db
        em.detach(updatedPaquete);
        updatedPaquete
            .codigoSeguimiento(UPDATED_CODIGO_SEGUIMIENTO)
            .descripcion(UPDATED_DESCRIPCION)
            .detalle(UPDATED_DETALLE)
            .peso(UPDATED_PESO)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .fechaEntregaEstimada(UPDATED_FECHA_ENTREGA_ESTIMADA)
            .estado(UPDATED_ESTADO)
            .tipoEntrega(UPDATED_TIPO_ENTREGA)
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .latitudEntrega(UPDATED_LATITUD_ENTREGA)
            .longitudEntrega(UPDATED_LONGITUD_ENTREGA)
            .confirmado(UPDATED_CONFIRMADO)
            .codigoQR(UPDATED_CODIGO_QR)
            .ubicacionActual(UPDATED_UBICACION_ACTUAL)
            .fragil(UPDATED_FRAGIL)
            .clienteTokenAcceso(UPDATED_CLIENTE_TOKEN_ACCESO);
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(updatedPaquete);

        restPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paqueteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaqueteToMatchAllProperties(updatedPaquete);
    }

    @Test
    @Transactional
    void putNonExistingPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paquete.setId(longCount.incrementAndGet());

        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paqueteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paquete.setId(longCount.incrementAndGet());

        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaqueteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paquete.setId(longCount.incrementAndGet());

        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaqueteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaqueteWithPatch() throws Exception {
        // Initialize the database
        insertedPaquete = paqueteRepository.saveAndFlush(paquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paquete using partial update
        Paquete partialUpdatedPaquete = new Paquete();
        partialUpdatedPaquete.setId(paquete.getId());

        partialUpdatedPaquete
            .descripcion(UPDATED_DESCRIPCION)
            .detalle(UPDATED_DETALLE)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .tipoEntrega(UPDATED_TIPO_ENTREGA)
            .latitudEntrega(UPDATED_LATITUD_ENTREGA)
            .ubicacionActual(UPDATED_UBICACION_ACTUAL)
            .fragil(UPDATED_FRAGIL)
            .clienteTokenAcceso(UPDATED_CLIENTE_TOKEN_ACCESO);

        restPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaquete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaquete))
            )
            .andExpect(status().isOk());

        // Validate the Paquete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaqueteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPaquete, paquete), getPersistedPaquete(paquete));
    }

    @Test
    @Transactional
    void fullUpdatePaqueteWithPatch() throws Exception {
        // Initialize the database
        insertedPaquete = paqueteRepository.saveAndFlush(paquete);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paquete using partial update
        Paquete partialUpdatedPaquete = new Paquete();
        partialUpdatedPaquete.setId(paquete.getId());

        partialUpdatedPaquete
            .codigoSeguimiento(UPDATED_CODIGO_SEGUIMIENTO)
            .descripcion(UPDATED_DESCRIPCION)
            .detalle(UPDATED_DETALLE)
            .peso(UPDATED_PESO)
            .fechaEnvio(UPDATED_FECHA_ENVIO)
            .fechaEntregaEstimada(UPDATED_FECHA_ENTREGA_ESTIMADA)
            .estado(UPDATED_ESTADO)
            .tipoEntrega(UPDATED_TIPO_ENTREGA)
            .direccionEntrega(UPDATED_DIRECCION_ENTREGA)
            .latitudEntrega(UPDATED_LATITUD_ENTREGA)
            .longitudEntrega(UPDATED_LONGITUD_ENTREGA)
            .confirmado(UPDATED_CONFIRMADO)
            .codigoQR(UPDATED_CODIGO_QR)
            .ubicacionActual(UPDATED_UBICACION_ACTUAL)
            .fragil(UPDATED_FRAGIL)
            .clienteTokenAcceso(UPDATED_CLIENTE_TOKEN_ACCESO);

        restPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaquete.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaquete))
            )
            .andExpect(status().isOk());

        // Validate the Paquete in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaqueteUpdatableFieldsEquals(partialUpdatedPaquete, getPersistedPaquete(partialUpdatedPaquete));
    }

    @Test
    @Transactional
    void patchNonExistingPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paquete.setId(longCount.incrementAndGet());

        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paqueteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paquete.setId(longCount.incrementAndGet());

        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaqueteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paqueteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaquete() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paquete.setId(longCount.incrementAndGet());

        // Create the Paquete
        PaqueteDTO paqueteDTO = paqueteMapper.toDto(paquete);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaqueteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paqueteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paquete in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaquete() throws Exception {
        // Initialize the database
        insertedPaquete = paqueteRepository.saveAndFlush(paquete);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paquete
        restPaqueteMockMvc
            .perform(delete(ENTITY_API_URL_ID, paquete.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paqueteRepository.count();
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

    protected Paquete getPersistedPaquete(Paquete paquete) {
        return paqueteRepository.findById(paquete.getId()).orElseThrow();
    }

    protected void assertPersistedPaqueteToMatchAllProperties(Paquete expectedPaquete) {
        assertPaqueteAllPropertiesEquals(expectedPaquete, getPersistedPaquete(expectedPaquete));
    }

    protected void assertPersistedPaqueteToMatchUpdatableProperties(Paquete expectedPaquete) {
        assertPaqueteAllUpdatablePropertiesEquals(expectedPaquete, getPersistedPaquete(expectedPaquete));
    }
}
