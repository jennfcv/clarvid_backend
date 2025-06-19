package com.paqueteria.web.rest;

import com.paqueteria.repository.RecepcionistaRepository;
import com.paqueteria.service.RecepcionistaService;
import com.paqueteria.service.dto.RecepcionistaDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.Recepcionista}.
 */
@RestController
@RequestMapping("/api/recepcionistas")
public class RecepcionistaResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecepcionistaResource.class);

    private static final String ENTITY_NAME = "recepcionista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecepcionistaService recepcionistaService;

    private final RecepcionistaRepository recepcionistaRepository;

    public RecepcionistaResource(RecepcionistaService recepcionistaService, RecepcionistaRepository recepcionistaRepository) {
        this.recepcionistaService = recepcionistaService;
        this.recepcionistaRepository = recepcionistaRepository;
    }

    /**
     * {@code POST  /recepcionistas} : Create a new recepcionista.
     *
     * @param recepcionistaDTO the recepcionistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recepcionistaDTO, or with status {@code 400 (Bad Request)} if the recepcionista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RecepcionistaDTO> createRecepcionista(@Valid @RequestBody RecepcionistaDTO recepcionistaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Recepcionista : {}", recepcionistaDTO);
        if (recepcionistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new recepcionista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        recepcionistaDTO = recepcionistaService.save(recepcionistaDTO);
        return ResponseEntity.created(new URI("/api/recepcionistas/" + recepcionistaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, recepcionistaDTO.getId().toString()))
            .body(recepcionistaDTO);
    }

    /**
     * {@code PUT  /recepcionistas/:id} : Updates an existing recepcionista.
     *
     * @param id the id of the recepcionistaDTO to save.
     * @param recepcionistaDTO the recepcionistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recepcionistaDTO,
     * or with status {@code 400 (Bad Request)} if the recepcionistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recepcionistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> updateRecepcionista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecepcionistaDTO recepcionistaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recepcionista : {}, {}", id, recepcionistaDTO);
        if (recepcionistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recepcionistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recepcionistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        recepcionistaDTO = recepcionistaService.update(recepcionistaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recepcionistaDTO.getId().toString()))
            .body(recepcionistaDTO);
    }

    /**
     * {@code PATCH  /recepcionistas/:id} : Partial updates given fields of an existing recepcionista, field will ignore if it is null
     *
     * @param id the id of the recepcionistaDTO to save.
     * @param recepcionistaDTO the recepcionistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recepcionistaDTO,
     * or with status {@code 400 (Bad Request)} if the recepcionistaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recepcionistaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recepcionistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecepcionistaDTO> partialUpdateRecepcionista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecepcionistaDTO recepcionistaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recepcionista partially : {}, {}", id, recepcionistaDTO);
        if (recepcionistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recepcionistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recepcionistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecepcionistaDTO> result = recepcionistaService.partialUpdate(recepcionistaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recepcionistaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recepcionistas} : get all the recepcionistas.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recepcionistas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RecepcionistaDTO>> getAllRecepcionistas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of Recepcionistas");
        Page<RecepcionistaDTO> page;
        if (eagerload) {
            page = recepcionistaService.findAllWithEagerRelationships(pageable);
        } else {
            page = recepcionistaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recepcionistas/:id} : get the "id" recepcionista.
     *
     * @param id the id of the recepcionistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recepcionistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> getRecepcionista(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recepcionista : {}", id);
        Optional<RecepcionistaDTO> recepcionistaDTO = recepcionistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recepcionistaDTO);
    }

    /**
     * {@code DELETE  /recepcionistas/:id} : delete the "id" recepcionista.
     *
     * @param id the id of the recepcionistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecepcionista(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recepcionista : {}", id);
        recepcionistaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
