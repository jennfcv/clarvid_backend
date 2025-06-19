package com.paqueteria.web.rest;

import com.paqueteria.repository.CalificacionEntregaRepository;
import com.paqueteria.service.CalificacionEntregaService;
import com.paqueteria.service.dto.CalificacionEntregaDTO;
import com.paqueteria.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.paqueteria.domain.CalificacionEntrega}.
 */
@RestController
@RequestMapping("/api/calificacion-entregas")
public class CalificacionEntregaResource {

    private static final Logger LOG = LoggerFactory.getLogger(CalificacionEntregaResource.class);

    private static final String ENTITY_NAME = "calificacionEntrega";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalificacionEntregaService calificacionEntregaService;

    private final CalificacionEntregaRepository calificacionEntregaRepository;

    public CalificacionEntregaResource(
        CalificacionEntregaService calificacionEntregaService,
        CalificacionEntregaRepository calificacionEntregaRepository
    ) {
        this.calificacionEntregaService = calificacionEntregaService;
        this.calificacionEntregaRepository = calificacionEntregaRepository;
    }

    /**
     * {@code POST  /calificacion-entregas} : Create a new calificacionEntrega.
     *
     * @param calificacionEntregaDTO the calificacionEntregaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calificacionEntregaDTO, or with status {@code 400 (Bad Request)} if the calificacionEntrega has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CalificacionEntregaDTO> createCalificacionEntrega(@RequestBody CalificacionEntregaDTO calificacionEntregaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CalificacionEntrega : {}", calificacionEntregaDTO);
        if (calificacionEntregaDTO.getId() != null) {
            throw new BadRequestAlertException("A new calificacionEntrega cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calificacionEntregaDTO = calificacionEntregaService.save(calificacionEntregaDTO);
        return ResponseEntity.created(new URI("/api/calificacion-entregas/" + calificacionEntregaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, calificacionEntregaDTO.getId().toString()))
            .body(calificacionEntregaDTO);
    }

    /**
     * {@code PUT  /calificacion-entregas/:id} : Updates an existing calificacionEntrega.
     *
     * @param id the id of the calificacionEntregaDTO to save.
     * @param calificacionEntregaDTO the calificacionEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calificacionEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the calificacionEntregaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calificacionEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalificacionEntregaDTO> updateCalificacionEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CalificacionEntregaDTO calificacionEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CalificacionEntrega : {}, {}", id, calificacionEntregaDTO);
        if (calificacionEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calificacionEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calificacionEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        calificacionEntregaDTO = calificacionEntregaService.update(calificacionEntregaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calificacionEntregaDTO.getId().toString()))
            .body(calificacionEntregaDTO);
    }

    /**
     * {@code PATCH  /calificacion-entregas/:id} : Partial updates given fields of an existing calificacionEntrega, field will ignore if it is null
     *
     * @param id the id of the calificacionEntregaDTO to save.
     * @param calificacionEntregaDTO the calificacionEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calificacionEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the calificacionEntregaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calificacionEntregaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calificacionEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalificacionEntregaDTO> partialUpdateCalificacionEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CalificacionEntregaDTO calificacionEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CalificacionEntrega partially : {}, {}", id, calificacionEntregaDTO);
        if (calificacionEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calificacionEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calificacionEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalificacionEntregaDTO> result = calificacionEntregaService.partialUpdate(calificacionEntregaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calificacionEntregaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /calificacion-entregas} : get all the calificacionEntregas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calificacionEntregas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CalificacionEntregaDTO>> getAllCalificacionEntregas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CalificacionEntregas");
        Page<CalificacionEntregaDTO> page = calificacionEntregaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calificacion-entregas/:id} : get the "id" calificacionEntrega.
     *
     * @param id the id of the calificacionEntregaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calificacionEntregaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionEntregaDTO> getCalificacionEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CalificacionEntrega : {}", id);
        Optional<CalificacionEntregaDTO> calificacionEntregaDTO = calificacionEntregaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calificacionEntregaDTO);
    }

    /**
     * {@code DELETE  /calificacion-entregas/:id} : delete the "id" calificacionEntrega.
     *
     * @param id the id of the calificacionEntregaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacionEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CalificacionEntrega : {}", id);
        calificacionEntregaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
