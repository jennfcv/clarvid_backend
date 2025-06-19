package com.paqueteria.web.rest;

import com.paqueteria.repository.SucursalRepository;
import com.paqueteria.service.SucursalService;
import com.paqueteria.service.dto.SucursalDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.Sucursal}.
 */
@RestController
@RequestMapping("/api/sucursals")
public class SucursalResource {

    private static final Logger LOG = LoggerFactory.getLogger(SucursalResource.class);

    private static final String ENTITY_NAME = "sucursal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SucursalService sucursalService;

    private final SucursalRepository sucursalRepository;

    public SucursalResource(SucursalService sucursalService, SucursalRepository sucursalRepository) {
        this.sucursalService = sucursalService;
        this.sucursalRepository = sucursalRepository;
    }

    /**
     * {@code POST  /sucursals} : Create a new sucursal.
     *
     * @param sucursalDTO the sucursalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sucursalDTO, or with status {@code 400 (Bad Request)} if the sucursal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SucursalDTO> createSucursal(@Valid @RequestBody SucursalDTO sucursalDTO) throws URISyntaxException {
        LOG.debug("REST request to save Sucursal : {}", sucursalDTO);
        if (sucursalDTO.getId() != null) {
            throw new BadRequestAlertException("A new sucursal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sucursalDTO = sucursalService.save(sucursalDTO);
        return ResponseEntity.created(new URI("/api/sucursals/" + sucursalDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sucursalDTO.getId().toString()))
            .body(sucursalDTO);
    }

    /**
     * {@code PUT  /sucursals/:id} : Updates an existing sucursal.
     *
     * @param id the id of the sucursalDTO to save.
     * @param sucursalDTO the sucursalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sucursalDTO,
     * or with status {@code 400 (Bad Request)} if the sucursalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sucursalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> updateSucursal(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SucursalDTO sucursalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Sucursal : {}, {}", id, sucursalDTO);
        if (sucursalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sucursalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sucursalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sucursalDTO = sucursalService.update(sucursalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sucursalDTO.getId().toString()))
            .body(sucursalDTO);
    }

    /**
     * {@code PATCH  /sucursals/:id} : Partial updates given fields of an existing sucursal, field will ignore if it is null
     *
     * @param id the id of the sucursalDTO to save.
     * @param sucursalDTO the sucursalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sucursalDTO,
     * or with status {@code 400 (Bad Request)} if the sucursalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sucursalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sucursalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SucursalDTO> partialUpdateSucursal(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SucursalDTO sucursalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Sucursal partially : {}, {}", id, sucursalDTO);
        if (sucursalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sucursalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sucursalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SucursalDTO> result = sucursalService.partialUpdate(sucursalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sucursalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sucursals} : get all the sucursals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sucursals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SucursalDTO>> getAllSucursals(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Sucursals");
        Page<SucursalDTO> page = sucursalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sucursals/:id} : get the "id" sucursal.
     *
     * @param id the id of the sucursalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sucursalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> getSucursal(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Sucursal : {}", id);
        Optional<SucursalDTO> sucursalDTO = sucursalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sucursalDTO);
    }

    /**
     * {@code DELETE  /sucursals/:id} : delete the "id" sucursal.
     *
     * @param id the id of the sucursalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Sucursal : {}", id);
        sucursalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
