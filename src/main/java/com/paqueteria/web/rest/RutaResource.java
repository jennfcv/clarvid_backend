package com.paqueteria.web.rest;

import com.paqueteria.repository.RutaRepository;
import com.paqueteria.service.RutaService;
import com.paqueteria.service.dto.RutaDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.Ruta}.
 */
@RestController
@RequestMapping("/api/rutas")
public class RutaResource {

    private static final Logger LOG = LoggerFactory.getLogger(RutaResource.class);

    private static final String ENTITY_NAME = "ruta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RutaService rutaService;

    private final RutaRepository rutaRepository;

    public RutaResource(RutaService rutaService, RutaRepository rutaRepository) {
        this.rutaService = rutaService;
        this.rutaRepository = rutaRepository;
    }

    /**
     * {@code POST  /rutas} : Create a new ruta.
     *
     * @param rutaDTO the rutaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rutaDTO, or with status {@code 400 (Bad Request)} if the ruta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RutaDTO> createRuta(@Valid @RequestBody RutaDTO rutaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Ruta : {}", rutaDTO);
        if (rutaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ruta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rutaDTO = rutaService.save(rutaDTO);
        return ResponseEntity.created(new URI("/api/rutas/" + rutaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rutaDTO.getId().toString()))
            .body(rutaDTO);
    }

    /**
     * {@code PUT  /rutas/:id} : Updates an existing ruta.
     *
     * @param id the id of the rutaDTO to save.
     * @param rutaDTO the rutaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rutaDTO,
     * or with status {@code 400 (Bad Request)} if the rutaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rutaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RutaDTO> updateRuta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RutaDTO rutaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Ruta : {}, {}", id, rutaDTO);
        if (rutaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rutaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rutaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rutaDTO = rutaService.update(rutaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rutaDTO.getId().toString()))
            .body(rutaDTO);
    }

    /**
     * {@code PATCH  /rutas/:id} : Partial updates given fields of an existing ruta, field will ignore if it is null
     *
     * @param id the id of the rutaDTO to save.
     * @param rutaDTO the rutaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rutaDTO,
     * or with status {@code 400 (Bad Request)} if the rutaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rutaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rutaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RutaDTO> partialUpdateRuta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RutaDTO rutaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Ruta partially : {}, {}", id, rutaDTO);
        if (rutaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rutaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rutaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RutaDTO> result = rutaService.partialUpdate(rutaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rutaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rutas} : get all the rutas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rutas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RutaDTO>> getAllRutas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Rutas");
        Page<RutaDTO> page = rutaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rutas/:id} : get the "id" ruta.
     *
     * @param id the id of the rutaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rutaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> getRuta(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Ruta : {}", id);
        Optional<RutaDTO> rutaDTO = rutaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rutaDTO);
    }

    /**
     * {@code DELETE  /rutas/:id} : delete the "id" ruta.
     *
     * @param id the id of the rutaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Ruta : {}", id);
        rutaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
