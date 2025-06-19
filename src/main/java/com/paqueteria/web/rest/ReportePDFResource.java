package com.paqueteria.web.rest;

import com.paqueteria.repository.ReportePDFRepository;
import com.paqueteria.service.ReportePDFService;
import com.paqueteria.service.dto.ReportePDFDTO;
import com.paqueteria.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.paqueteria.domain.ReportePDF}.
 */
@RestController
@RequestMapping("/api/reporte-pdfs")
public class ReportePDFResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReportePDFResource.class);

    private static final String ENTITY_NAME = "reportePDF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportePDFService reportePDFService;

    private final ReportePDFRepository reportePDFRepository;

    public ReportePDFResource(ReportePDFService reportePDFService, ReportePDFRepository reportePDFRepository) {
        this.reportePDFService = reportePDFService;
        this.reportePDFRepository = reportePDFRepository;
    }

    /**
     * {@code POST  /reporte-pdfs} : Create a new reportePDF.
     *
     * @param reportePDFDTO the reportePDFDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportePDFDTO, or with status {@code 400 (Bad Request)} if the reportePDF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ReportePDFDTO> createReportePDF(@Valid @RequestBody ReportePDFDTO reportePDFDTO) throws URISyntaxException {
        LOG.debug("REST request to save ReportePDF : {}", reportePDFDTO);
        if (reportePDFDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportePDF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reportePDFDTO = reportePDFService.save(reportePDFDTO);
        return ResponseEntity.created(new URI("/api/reporte-pdfs/" + reportePDFDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, reportePDFDTO.getId().toString()))
            .body(reportePDFDTO);
    }

    /**
     * {@code PUT  /reporte-pdfs/:id} : Updates an existing reportePDF.
     *
     * @param id the id of the reportePDFDTO to save.
     * @param reportePDFDTO the reportePDFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportePDFDTO,
     * or with status {@code 400 (Bad Request)} if the reportePDFDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportePDFDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReportePDFDTO> updateReportePDF(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReportePDFDTO reportePDFDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ReportePDF : {}, {}", id, reportePDFDTO);
        if (reportePDFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportePDFDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportePDFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        reportePDFDTO = reportePDFService.update(reportePDFDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportePDFDTO.getId().toString()))
            .body(reportePDFDTO);
    }

    /**
     * {@code PATCH  /reporte-pdfs/:id} : Partial updates given fields of an existing reportePDF, field will ignore if it is null
     *
     * @param id the id of the reportePDFDTO to save.
     * @param reportePDFDTO the reportePDFDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportePDFDTO,
     * or with status {@code 400 (Bad Request)} if the reportePDFDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reportePDFDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reportePDFDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReportePDFDTO> partialUpdateReportePDF(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReportePDFDTO reportePDFDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ReportePDF partially : {}, {}", id, reportePDFDTO);
        if (reportePDFDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportePDFDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportePDFRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReportePDFDTO> result = reportePDFService.partialUpdate(reportePDFDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportePDFDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reporte-pdfs} : get all the reportePDFS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportePDFS in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ReportePDFDTO>> getAllReportePDFS(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of ReportePDFS");
        Page<ReportePDFDTO> page = reportePDFService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reporte-pdfs/:id} : get the "id" reportePDF.
     *
     * @param id the id of the reportePDFDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportePDFDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReportePDFDTO> getReportePDF(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ReportePDF : {}", id);
        Optional<ReportePDFDTO> reportePDFDTO = reportePDFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportePDFDTO);
    }

    /**
     * {@code DELETE  /reporte-pdfs/:id} : delete the "id" reportePDF.
     *
     * @param id the id of the reportePDFDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportePDF(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ReportePDF : {}", id);
        reportePDFService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
