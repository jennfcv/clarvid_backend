package com.paqueteria.web.rest;

import com.paqueteria.repository.UbicacionRepartidorRepository;
import com.paqueteria.service.UbicacionRepartidorService;
import com.paqueteria.service.dto.UbicacionRepartidorDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.UbicacionRepartidor}.
 */
@RestController
@RequestMapping("/api/ubicacion-repartidors")
public class UbicacionRepartidorResource {

    private static final Logger LOG = LoggerFactory.getLogger(UbicacionRepartidorResource.class);

    private static final String ENTITY_NAME = "ubicacionRepartidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UbicacionRepartidorService ubicacionRepartidorService;

    private final UbicacionRepartidorRepository ubicacionRepartidorRepository;

    public UbicacionRepartidorResource(
        UbicacionRepartidorService ubicacionRepartidorService,
        UbicacionRepartidorRepository ubicacionRepartidorRepository
    ) {
        this.ubicacionRepartidorService = ubicacionRepartidorService;
        this.ubicacionRepartidorRepository = ubicacionRepartidorRepository;
    }

    /**
     * {@code POST  /ubicacion-repartidors} : Create a new ubicacionRepartidor.
     *
     * @param ubicacionRepartidorDTO the ubicacionRepartidorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ubicacionRepartidorDTO, or with status {@code 400 (Bad Request)} if the ubicacionRepartidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UbicacionRepartidorDTO> createUbicacionRepartidor(
        @Valid @RequestBody UbicacionRepartidorDTO ubicacionRepartidorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save UbicacionRepartidor : {}", ubicacionRepartidorDTO);
        if (ubicacionRepartidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new ubicacionRepartidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ubicacionRepartidorDTO = ubicacionRepartidorService.save(ubicacionRepartidorDTO);
        return ResponseEntity.created(new URI("/api/ubicacion-repartidors/" + ubicacionRepartidorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ubicacionRepartidorDTO.getId().toString()))
            .body(ubicacionRepartidorDTO);
    }

    /**
     * {@code PUT  /ubicacion-repartidors/:id} : Updates an existing ubicacionRepartidor.
     *
     * @param id the id of the ubicacionRepartidorDTO to save.
     * @param ubicacionRepartidorDTO the ubicacionRepartidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ubicacionRepartidorDTO,
     * or with status {@code 400 (Bad Request)} if the ubicacionRepartidorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ubicacionRepartidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionRepartidorDTO> updateUbicacionRepartidor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UbicacionRepartidorDTO ubicacionRepartidorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update UbicacionRepartidor : {}, {}", id, ubicacionRepartidorDTO);
        if (ubicacionRepartidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ubicacionRepartidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ubicacionRepartidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ubicacionRepartidorDTO = ubicacionRepartidorService.update(ubicacionRepartidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ubicacionRepartidorDTO.getId().toString()))
            .body(ubicacionRepartidorDTO);
    }

    /**
     * {@code PATCH  /ubicacion-repartidors/:id} : Partial updates given fields of an existing ubicacionRepartidor, field will ignore if it is null
     *
     * @param id the id of the ubicacionRepartidorDTO to save.
     * @param ubicacionRepartidorDTO the ubicacionRepartidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ubicacionRepartidorDTO,
     * or with status {@code 400 (Bad Request)} if the ubicacionRepartidorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ubicacionRepartidorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ubicacionRepartidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UbicacionRepartidorDTO> partialUpdateUbicacionRepartidor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UbicacionRepartidorDTO ubicacionRepartidorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UbicacionRepartidor partially : {}, {}", id, ubicacionRepartidorDTO);
        if (ubicacionRepartidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ubicacionRepartidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ubicacionRepartidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UbicacionRepartidorDTO> result = ubicacionRepartidorService.partialUpdate(ubicacionRepartidorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ubicacionRepartidorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ubicacion-repartidors} : get all the ubicacionRepartidors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ubicacionRepartidors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UbicacionRepartidorDTO>> getAllUbicacionRepartidors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of UbicacionRepartidors");
        Page<UbicacionRepartidorDTO> page = ubicacionRepartidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ubicacion-repartidors/:id} : get the "id" ubicacionRepartidor.
     *
     * @param id the id of the ubicacionRepartidorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ubicacionRepartidorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionRepartidorDTO> getUbicacionRepartidor(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UbicacionRepartidor : {}", id);
        Optional<UbicacionRepartidorDTO> ubicacionRepartidorDTO = ubicacionRepartidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ubicacionRepartidorDTO);
    }

    /**
     * {@code DELETE  /ubicacion-repartidors/:id} : delete the "id" ubicacionRepartidor.
     *
     * @param id the id of the ubicacionRepartidorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUbicacionRepartidor(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UbicacionRepartidor : {}", id);
        ubicacionRepartidorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
