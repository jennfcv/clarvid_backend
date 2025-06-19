package com.paqueteria.web.rest;

import com.paqueteria.repository.IntentoEntregaRepository;
import com.paqueteria.service.IntentoEntregaService;
import com.paqueteria.service.dto.IntentoEntregaDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.IntentoEntrega}.
 */
@RestController
@RequestMapping("/api/intento-entregas")
public class IntentoEntregaResource {

    private static final Logger LOG = LoggerFactory.getLogger(IntentoEntregaResource.class);

    private static final String ENTITY_NAME = "intentoEntrega";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntentoEntregaService intentoEntregaService;

    private final IntentoEntregaRepository intentoEntregaRepository;

    public IntentoEntregaResource(IntentoEntregaService intentoEntregaService, IntentoEntregaRepository intentoEntregaRepository) {
        this.intentoEntregaService = intentoEntregaService;
        this.intentoEntregaRepository = intentoEntregaRepository;
    }

    /**
     * {@code POST  /intento-entregas} : Create a new intentoEntrega.
     *
     * @param intentoEntregaDTO the intentoEntregaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intentoEntregaDTO, or with status {@code 400 (Bad Request)} if the intentoEntrega has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntentoEntregaDTO> createIntentoEntrega(@Valid @RequestBody IntentoEntregaDTO intentoEntregaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save IntentoEntrega : {}", intentoEntregaDTO);
        if (intentoEntregaDTO.getId() != null) {
            throw new BadRequestAlertException("A new intentoEntrega cannot already have an ID", ENTITY_NAME, "idexists");
        }
        intentoEntregaDTO = intentoEntregaService.save(intentoEntregaDTO);
        return ResponseEntity.created(new URI("/api/intento-entregas/" + intentoEntregaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, intentoEntregaDTO.getId().toString()))
            .body(intentoEntregaDTO);
    }

    /**
     * {@code PUT  /intento-entregas/:id} : Updates an existing intentoEntrega.
     *
     * @param id the id of the intentoEntregaDTO to save.
     * @param intentoEntregaDTO the intentoEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intentoEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the intentoEntregaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intentoEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntentoEntregaDTO> updateIntentoEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntentoEntregaDTO intentoEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update IntentoEntrega : {}, {}", id, intentoEntregaDTO);
        if (intentoEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intentoEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intentoEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        intentoEntregaDTO = intentoEntregaService.update(intentoEntregaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intentoEntregaDTO.getId().toString()))
            .body(intentoEntregaDTO);
    }

    /**
     * {@code PATCH  /intento-entregas/:id} : Partial updates given fields of an existing intentoEntrega, field will ignore if it is null
     *
     * @param id the id of the intentoEntregaDTO to save.
     * @param intentoEntregaDTO the intentoEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intentoEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the intentoEntregaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the intentoEntregaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the intentoEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntentoEntregaDTO> partialUpdateIntentoEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntentoEntregaDTO intentoEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IntentoEntrega partially : {}, {}", id, intentoEntregaDTO);
        if (intentoEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intentoEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intentoEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntentoEntregaDTO> result = intentoEntregaService.partialUpdate(intentoEntregaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intentoEntregaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /intento-entregas} : get all the intentoEntregas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intentoEntregas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IntentoEntregaDTO>> getAllIntentoEntregas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of IntentoEntregas");
        Page<IntentoEntregaDTO> page = intentoEntregaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intento-entregas/:id} : get the "id" intentoEntrega.
     *
     * @param id the id of the intentoEntregaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intentoEntregaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntentoEntregaDTO> getIntentoEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IntentoEntrega : {}", id);
        Optional<IntentoEntregaDTO> intentoEntregaDTO = intentoEntregaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intentoEntregaDTO);
    }

    /**
     * {@code DELETE  /intento-entregas/:id} : delete the "id" intentoEntrega.
     *
     * @param id the id of the intentoEntregaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntentoEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IntentoEntrega : {}", id);
        intentoEntregaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
