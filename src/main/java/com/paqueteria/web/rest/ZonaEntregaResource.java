package com.paqueteria.web.rest;

import com.paqueteria.repository.ZonaEntregaRepository;
import com.paqueteria.service.ZonaEntregaService;
import com.paqueteria.service.dto.ZonaEntregaDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.ZonaEntrega}.
 */
@RestController
@RequestMapping("/api/zona-entregas")
public class ZonaEntregaResource {

    private static final Logger LOG = LoggerFactory.getLogger(ZonaEntregaResource.class);

    private static final String ENTITY_NAME = "zonaEntrega";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZonaEntregaService zonaEntregaService;

    private final ZonaEntregaRepository zonaEntregaRepository;

    public ZonaEntregaResource(ZonaEntregaService zonaEntregaService, ZonaEntregaRepository zonaEntregaRepository) {
        this.zonaEntregaService = zonaEntregaService;
        this.zonaEntregaRepository = zonaEntregaRepository;
    }

    /**
     * {@code POST  /zona-entregas} : Create a new zonaEntrega.
     *
     * @param zonaEntregaDTO the zonaEntregaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zonaEntregaDTO, or with status {@code 400 (Bad Request)} if the zonaEntrega has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ZonaEntregaDTO> createZonaEntrega(@Valid @RequestBody ZonaEntregaDTO zonaEntregaDTO) throws URISyntaxException {
        LOG.debug("REST request to save ZonaEntrega : {}", zonaEntregaDTO);
        if (zonaEntregaDTO.getId() != null) {
            throw new BadRequestAlertException("A new zonaEntrega cannot already have an ID", ENTITY_NAME, "idexists");
        }
        zonaEntregaDTO = zonaEntregaService.save(zonaEntregaDTO);
        return ResponseEntity.created(new URI("/api/zona-entregas/" + zonaEntregaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, zonaEntregaDTO.getId().toString()))
            .body(zonaEntregaDTO);
    }

    /**
     * {@code PUT  /zona-entregas/:id} : Updates an existing zonaEntrega.
     *
     * @param id the id of the zonaEntregaDTO to save.
     * @param zonaEntregaDTO the zonaEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zonaEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the zonaEntregaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zonaEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ZonaEntregaDTO> updateZonaEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ZonaEntregaDTO zonaEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ZonaEntrega : {}, {}", id, zonaEntregaDTO);
        if (zonaEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zonaEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zonaEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        zonaEntregaDTO = zonaEntregaService.update(zonaEntregaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zonaEntregaDTO.getId().toString()))
            .body(zonaEntregaDTO);
    }

    /**
     * {@code PATCH  /zona-entregas/:id} : Partial updates given fields of an existing zonaEntrega, field will ignore if it is null
     *
     * @param id the id of the zonaEntregaDTO to save.
     * @param zonaEntregaDTO the zonaEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zonaEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the zonaEntregaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the zonaEntregaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the zonaEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ZonaEntregaDTO> partialUpdateZonaEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ZonaEntregaDTO zonaEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ZonaEntrega partially : {}, {}", id, zonaEntregaDTO);
        if (zonaEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, zonaEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zonaEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ZonaEntregaDTO> result = zonaEntregaService.partialUpdate(zonaEntregaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zonaEntregaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /zona-entregas} : get all the zonaEntregas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zonaEntregas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ZonaEntregaDTO>> getAllZonaEntregas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of ZonaEntregas");
        Page<ZonaEntregaDTO> page = zonaEntregaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /zona-entregas/:id} : get the "id" zonaEntrega.
     *
     * @param id the id of the zonaEntregaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zonaEntregaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ZonaEntregaDTO> getZonaEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ZonaEntrega : {}", id);
        Optional<ZonaEntregaDTO> zonaEntregaDTO = zonaEntregaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zonaEntregaDTO);
    }

    /**
     * {@code DELETE  /zona-entregas/:id} : delete the "id" zonaEntrega.
     *
     * @param id the id of the zonaEntregaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZonaEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ZonaEntrega : {}", id);
        zonaEntregaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
