package com.paqueteria.web.rest;

import com.paqueteria.repository.SeguimientoPaqueteRepository;
import com.paqueteria.service.SeguimientoPaqueteService;
import com.paqueteria.service.dto.SeguimientoPaqueteDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.SeguimientoPaquete}.
 */
@RestController
@RequestMapping("/api/seguimiento-paquetes")
public class SeguimientoPaqueteResource {

    private static final Logger LOG = LoggerFactory.getLogger(SeguimientoPaqueteResource.class);

    private static final String ENTITY_NAME = "seguimientoPaquete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeguimientoPaqueteService seguimientoPaqueteService;

    private final SeguimientoPaqueteRepository seguimientoPaqueteRepository;

    public SeguimientoPaqueteResource(
        SeguimientoPaqueteService seguimientoPaqueteService,
        SeguimientoPaqueteRepository seguimientoPaqueteRepository
    ) {
        this.seguimientoPaqueteService = seguimientoPaqueteService;
        this.seguimientoPaqueteRepository = seguimientoPaqueteRepository;
    }

    /**
     * {@code POST  /seguimiento-paquetes} : Create a new seguimientoPaquete.
     *
     * @param seguimientoPaqueteDTO the seguimientoPaqueteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seguimientoPaqueteDTO, or with status {@code 400 (Bad Request)} if the seguimientoPaquete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SeguimientoPaqueteDTO> createSeguimientoPaquete(@Valid @RequestBody SeguimientoPaqueteDTO seguimientoPaqueteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SeguimientoPaquete : {}", seguimientoPaqueteDTO);
        if (seguimientoPaqueteDTO.getId() != null) {
            throw new BadRequestAlertException("A new seguimientoPaquete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        seguimientoPaqueteDTO = seguimientoPaqueteService.save(seguimientoPaqueteDTO);
        return ResponseEntity.created(new URI("/api/seguimiento-paquetes/" + seguimientoPaqueteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, seguimientoPaqueteDTO.getId().toString()))
            .body(seguimientoPaqueteDTO);
    }

    /**
     * {@code PUT  /seguimiento-paquetes/:id} : Updates an existing seguimientoPaquete.
     *
     * @param id the id of the seguimientoPaqueteDTO to save.
     * @param seguimientoPaqueteDTO the seguimientoPaqueteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguimientoPaqueteDTO,
     * or with status {@code 400 (Bad Request)} if the seguimientoPaqueteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seguimientoPaqueteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoPaqueteDTO> updateSeguimientoPaquete(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SeguimientoPaqueteDTO seguimientoPaqueteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SeguimientoPaquete : {}, {}", id, seguimientoPaqueteDTO);
        if (seguimientoPaqueteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seguimientoPaqueteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seguimientoPaqueteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        seguimientoPaqueteDTO = seguimientoPaqueteService.update(seguimientoPaqueteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seguimientoPaqueteDTO.getId().toString()))
            .body(seguimientoPaqueteDTO);
    }

    /**
     * {@code PATCH  /seguimiento-paquetes/:id} : Partial updates given fields of an existing seguimientoPaquete, field will ignore if it is null
     *
     * @param id the id of the seguimientoPaqueteDTO to save.
     * @param seguimientoPaqueteDTO the seguimientoPaqueteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguimientoPaqueteDTO,
     * or with status {@code 400 (Bad Request)} if the seguimientoPaqueteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the seguimientoPaqueteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the seguimientoPaqueteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SeguimientoPaqueteDTO> partialUpdateSeguimientoPaquete(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SeguimientoPaqueteDTO seguimientoPaqueteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SeguimientoPaquete partially : {}, {}", id, seguimientoPaqueteDTO);
        if (seguimientoPaqueteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seguimientoPaqueteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seguimientoPaqueteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeguimientoPaqueteDTO> result = seguimientoPaqueteService.partialUpdate(seguimientoPaqueteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, seguimientoPaqueteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /seguimiento-paquetes} : get all the seguimientoPaquetes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seguimientoPaquetes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SeguimientoPaqueteDTO>> getAllSeguimientoPaquetes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SeguimientoPaquetes");
        Page<SeguimientoPaqueteDTO> page = seguimientoPaqueteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seguimiento-paquetes/:id} : get the "id" seguimientoPaquete.
     *
     * @param id the id of the seguimientoPaqueteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seguimientoPaqueteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoPaqueteDTO> getSeguimientoPaquete(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SeguimientoPaquete : {}", id);
        Optional<SeguimientoPaqueteDTO> seguimientoPaqueteDTO = seguimientoPaqueteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seguimientoPaqueteDTO);
    }

    /**
     * {@code DELETE  /seguimiento-paquetes/:id} : delete the "id" seguimientoPaquete.
     *
     * @param id the id of the seguimientoPaqueteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguimientoPaquete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SeguimientoPaquete : {}", id);
        seguimientoPaqueteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
