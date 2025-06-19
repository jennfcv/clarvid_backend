package com.paqueteria.web.rest;

import com.paqueteria.repository.RepartidorRepository;
import com.paqueteria.service.RepartidorService;
import com.paqueteria.service.dto.RepartidorDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.Repartidor}.
 */
@RestController
@RequestMapping("/api/repartidors")
public class RepartidorResource {

    private static final Logger LOG = LoggerFactory.getLogger(RepartidorResource.class);

    private static final String ENTITY_NAME = "repartidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepartidorService repartidorService;

    private final RepartidorRepository repartidorRepository;

    public RepartidorResource(RepartidorService repartidorService, RepartidorRepository repartidorRepository) {
        this.repartidorService = repartidorService;
        this.repartidorRepository = repartidorRepository;
    }

    /**
     * {@code POST  /repartidors} : Create a new repartidor.
     *
     * @param repartidorDTO the repartidorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repartidorDTO, or with status {@code 400 (Bad Request)} if the repartidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RepartidorDTO> createRepartidor(@Valid @RequestBody RepartidorDTO repartidorDTO) throws URISyntaxException {
        LOG.debug("REST request to save Repartidor : {}", repartidorDTO);
        if (repartidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new repartidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        repartidorDTO = repartidorService.save(repartidorDTO);
        return ResponseEntity.created(new URI("/api/repartidors/" + repartidorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, repartidorDTO.getId().toString()))
            .body(repartidorDTO);
    }

    /**
     * {@code PUT  /repartidors/:id} : Updates an existing repartidor.
     *
     * @param id the id of the repartidorDTO to save.
     * @param repartidorDTO the repartidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repartidorDTO,
     * or with status {@code 400 (Bad Request)} if the repartidorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repartidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RepartidorDTO> updateRepartidor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RepartidorDTO repartidorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Repartidor : {}, {}", id, repartidorDTO);
        if (repartidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repartidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repartidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        repartidorDTO = repartidorService.update(repartidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, repartidorDTO.getId().toString()))
            .body(repartidorDTO);
    }

    /**
     * {@code PATCH  /repartidors/:id} : Partial updates given fields of an existing repartidor, field will ignore if it is null
     *
     * @param id the id of the repartidorDTO to save.
     * @param repartidorDTO the repartidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repartidorDTO,
     * or with status {@code 400 (Bad Request)} if the repartidorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the repartidorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the repartidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RepartidorDTO> partialUpdateRepartidor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RepartidorDTO repartidorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Repartidor partially : {}, {}", id, repartidorDTO);
        if (repartidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repartidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repartidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RepartidorDTO> result = repartidorService.partialUpdate(repartidorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, repartidorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /repartidors} : get all the repartidors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repartidors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RepartidorDTO>> getAllRepartidors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Repartidors");
        Page<RepartidorDTO> page;
        if (eagerload) {
            page = repartidorService.findAllWithEagerRelationships(pageable);
        } else {
            page = repartidorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /repartidors/:id} : get the "id" repartidor.
     *
     * @param id the id of the repartidorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repartidorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RepartidorDTO> getRepartidor(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Repartidor : {}", id);
        Optional<RepartidorDTO> repartidorDTO = repartidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(repartidorDTO);
    }

    /**
     * {@code DELETE  /repartidors/:id} : delete the "id" repartidor.
     *
     * @param id the id of the repartidorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepartidor(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Repartidor : {}", id);
        repartidorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
