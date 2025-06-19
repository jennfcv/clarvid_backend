package com.paqueteria.web.rest;

import static com.paqueteria.domain.ReportePDFAsserts.*;
import static com.paqueteria.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paqueteria.IntegrationTest;
import com.paqueteria.domain.ReportePDF;
import com.paqueteria.repository.ReportePDFRepository;
import com.paqueteria.service.dto.ReportePDFDTO;
import com.paqueteria.service.mapper.ReportePDFMapper;
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
 * Integration tests for the {@link ReportePDFResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportePDFResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ARCHIVO = "BBBBBBBBBB";

    private static final String DEFAULT_RUTA_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_RUTA_ARCHIVO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_GENERACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_GENERACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/reporte-pdfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReportePDFRepository reportePDFRepository;

    @Autowired
    private ReportePDFMapper reportePDFMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportePDFMockMvc;

    private ReportePDF reportePDF;

    private ReportePDF insertedReportePDF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportePDF createEntity() {
        return new ReportePDF()
            .tipo(DEFAULT_TIPO)
            .nombreArchivo(DEFAULT_NOMBRE_ARCHIVO)
            .rutaArchivo(DEFAULT_RUTA_ARCHIVO)
            .fechaGeneracion(DEFAULT_FECHA_GENERACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportePDF createUpdatedEntity() {
        return new ReportePDF()
            .tipo(UPDATED_TIPO)
            .nombreArchivo(UPDATED_NOMBRE_ARCHIVO)
            .rutaArchivo(UPDATED_RUTA_ARCHIVO)
            .fechaGeneracion(UPDATED_FECHA_GENERACION);
    }

    @BeforeEach
    void initTest() {
        reportePDF = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedReportePDF != null) {
            reportePDFRepository.delete(insertedReportePDF);
            insertedReportePDF = null;
        }
    }

    @Test
    @Transactional
    void createReportePDF() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);
        var returnedReportePDFDTO = om.readValue(
            restReportePDFMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportePDFDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReportePDFDTO.class
        );

        // Validate the ReportePDF in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReportePDF = reportePDFMapper.toEntity(returnedReportePDFDTO);
        assertReportePDFUpdatableFieldsEquals(returnedReportePDF, getPersistedReportePDF(returnedReportePDF));

        insertedReportePDF = returnedReportePDF;
    }

    @Test
    @Transactional
    void createReportePDFWithExistingId() throws Exception {
        // Create the ReportePDF with an existing ID
        reportePDF.setId(1L);
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportePDFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportePDFDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reportePDF.setTipo(null);

        // Create the ReportePDF, which fails.
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        restReportePDFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportePDFDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreArchivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reportePDF.setNombreArchivo(null);

        // Create the ReportePDF, which fails.
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        restReportePDFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportePDFDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaGeneracionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        reportePDF.setFechaGeneracion(null);

        // Create the ReportePDF, which fails.
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        restReportePDFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportePDFDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReportePDFS() throws Exception {
        // Initialize the database
        insertedReportePDF = reportePDFRepository.saveAndFlush(reportePDF);

        // Get all the reportePDFList
        restReportePDFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportePDF.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].nombreArchivo").value(hasItem(DEFAULT_NOMBRE_ARCHIVO)))
            .andExpect(jsonPath("$.[*].rutaArchivo").value(hasItem(DEFAULT_RUTA_ARCHIVO)))
            .andExpect(jsonPath("$.[*].fechaGeneracion").value(hasItem(DEFAULT_FECHA_GENERACION.toString())));
    }

    @Test
    @Transactional
    void getReportePDF() throws Exception {
        // Initialize the database
        insertedReportePDF = reportePDFRepository.saveAndFlush(reportePDF);

        // Get the reportePDF
        restReportePDFMockMvc
            .perform(get(ENTITY_API_URL_ID, reportePDF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportePDF.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.nombreArchivo").value(DEFAULT_NOMBRE_ARCHIVO))
            .andExpect(jsonPath("$.rutaArchivo").value(DEFAULT_RUTA_ARCHIVO))
            .andExpect(jsonPath("$.fechaGeneracion").value(DEFAULT_FECHA_GENERACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReportePDF() throws Exception {
        // Get the reportePDF
        restReportePDFMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReportePDF() throws Exception {
        // Initialize the database
        insertedReportePDF = reportePDFRepository.saveAndFlush(reportePDF);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reportePDF
        ReportePDF updatedReportePDF = reportePDFRepository.findById(reportePDF.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReportePDF are not directly saved in db
        em.detach(updatedReportePDF);
        updatedReportePDF
            .tipo(UPDATED_TIPO)
            .nombreArchivo(UPDATED_NOMBRE_ARCHIVO)
            .rutaArchivo(UPDATED_RUTA_ARCHIVO)
            .fechaGeneracion(UPDATED_FECHA_GENERACION);
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(updatedReportePDF);

        restReportePDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportePDFDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reportePDFDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReportePDFToMatchAllProperties(updatedReportePDF);
    }

    @Test
    @Transactional
    void putNonExistingReportePDF() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reportePDF.setId(longCount.incrementAndGet());

        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportePDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportePDFDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reportePDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReportePDF() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reportePDF.setId(longCount.incrementAndGet());

        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportePDFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reportePDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReportePDF() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reportePDF.setId(longCount.incrementAndGet());

        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportePDFMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportePDFDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReportePDFWithPatch() throws Exception {
        // Initialize the database
        insertedReportePDF = reportePDFRepository.saveAndFlush(reportePDF);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reportePDF using partial update
        ReportePDF partialUpdatedReportePDF = new ReportePDF();
        partialUpdatedReportePDF.setId(reportePDF.getId());

        partialUpdatedReportePDF.rutaArchivo(UPDATED_RUTA_ARCHIVO).fechaGeneracion(UPDATED_FECHA_GENERACION);

        restReportePDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportePDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReportePDF))
            )
            .andExpect(status().isOk());

        // Validate the ReportePDF in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReportePDFUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReportePDF, reportePDF),
            getPersistedReportePDF(reportePDF)
        );
    }

    @Test
    @Transactional
    void fullUpdateReportePDFWithPatch() throws Exception {
        // Initialize the database
        insertedReportePDF = reportePDFRepository.saveAndFlush(reportePDF);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reportePDF using partial update
        ReportePDF partialUpdatedReportePDF = new ReportePDF();
        partialUpdatedReportePDF.setId(reportePDF.getId());

        partialUpdatedReportePDF
            .tipo(UPDATED_TIPO)
            .nombreArchivo(UPDATED_NOMBRE_ARCHIVO)
            .rutaArchivo(UPDATED_RUTA_ARCHIVO)
            .fechaGeneracion(UPDATED_FECHA_GENERACION);

        restReportePDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportePDF.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReportePDF))
            )
            .andExpect(status().isOk());

        // Validate the ReportePDF in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReportePDFUpdatableFieldsEquals(partialUpdatedReportePDF, getPersistedReportePDF(partialUpdatedReportePDF));
    }

    @Test
    @Transactional
    void patchNonExistingReportePDF() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reportePDF.setId(longCount.incrementAndGet());

        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportePDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportePDFDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reportePDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReportePDF() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reportePDF.setId(longCount.incrementAndGet());

        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportePDFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reportePDFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReportePDF() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reportePDF.setId(longCount.incrementAndGet());

        // Create the ReportePDF
        ReportePDFDTO reportePDFDTO = reportePDFMapper.toDto(reportePDF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportePDFMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reportePDFDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportePDF in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReportePDF() throws Exception {
        // Initialize the database
        insertedReportePDF = reportePDFRepository.saveAndFlush(reportePDF);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reportePDF
        restReportePDFMockMvc
            .perform(delete(ENTITY_API_URL_ID, reportePDF.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reportePDFRepository.count();
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

    protected ReportePDF getPersistedReportePDF(ReportePDF reportePDF) {
        return reportePDFRepository.findById(reportePDF.getId()).orElseThrow();
    }

    protected void assertPersistedReportePDFToMatchAllProperties(ReportePDF expectedReportePDF) {
        assertReportePDFAllPropertiesEquals(expectedReportePDF, getPersistedReportePDF(expectedReportePDF));
    }

    protected void assertPersistedReportePDFToMatchUpdatableProperties(ReportePDF expectedReportePDF) {
        assertReportePDFAllUpdatablePropertiesEquals(expectedReportePDF, getPersistedReportePDF(expectedReportePDF));
    }
}
