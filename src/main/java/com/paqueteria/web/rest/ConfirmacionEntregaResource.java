package com.paqueteria.web.rest;

import com.paqueteria.repository.ConfirmacionEntregaRepository;
import com.paqueteria.service.ConfirmacionEntregaService;
import com.paqueteria.service.dto.ConfirmacionEntregaDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.ConfirmacionEntrega}.
 */
@RestController
@RequestMapping("/api/confirmacion-entregas")
public class ConfirmacionEntregaResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmacionEntregaResource.class);

    private static final String ENTITY_NAME = "confirmacionEntrega";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfirmacionEntregaService confirmacionEntregaService;

    private final ConfirmacionEntregaRepository confirmacionEntregaRepository;

    public ConfirmacionEntregaResource(
        ConfirmacionEntregaService confirmacionEntregaService,
        ConfirmacionEntregaRepository confirmacionEntregaRepository
    ) {
        this.confirmacionEntregaService = confirmacionEntregaService;
        this.confirmacionEntregaRepository = confirmacionEntregaRepository;
    }

    /**
     * {@code POST  /confirmacion-entregas} : Create a new confirmacionEntrega.
     *
     * @param confirmacionEntregaDTO the confirmacionEntregaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new confirmacionEntregaDTO, or with status {@code 400 (Bad Request)} if the confirmacionEntrega has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConfirmacionEntregaDTO> createConfirmacionEntrega(
        @Valid @RequestBody ConfirmacionEntregaDTO confirmacionEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ConfirmacionEntrega : {}", confirmacionEntregaDTO);
        if (confirmacionEntregaDTO.getId() != null) {
            throw new BadRequestAlertException("A new confirmacionEntrega cannot already have an ID", ENTITY_NAME, "idexists");
        }
        confirmacionEntregaDTO = confirmacionEntregaService.save(confirmacionEntregaDTO);
        return ResponseEntity.created(new URI("/api/confirmacion-entregas/" + confirmacionEntregaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, confirmacionEntregaDTO.getId().toString()))
            .body(confirmacionEntregaDTO);
    }

    /**
     * {@code PUT  /confirmacion-entregas/:id} : Updates an existing confirmacionEntrega.
     *
     * @param id the id of the confirmacionEntregaDTO to save.
     * @param confirmacionEntregaDTO the confirmacionEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confirmacionEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the confirmacionEntregaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the confirmacionEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConfirmacionEntregaDTO> updateConfirmacionEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConfirmacionEntregaDTO confirmacionEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ConfirmacionEntrega : {}, {}", id, confirmacionEntregaDTO);
        if (confirmacionEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confirmacionEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confirmacionEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        confirmacionEntregaDTO = confirmacionEntregaService.update(confirmacionEntregaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, confirmacionEntregaDTO.getId().toString()))
            .body(confirmacionEntregaDTO);
    }

    /**
     * {@code PATCH  /confirmacion-entregas/:id} : Partial updates given fields of an existing confirmacionEntrega, field will ignore if it is null
     *
     * @param id the id of the confirmacionEntregaDTO to save.
     * @param confirmacionEntregaDTO the confirmacionEntregaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confirmacionEntregaDTO,
     * or with status {@code 400 (Bad Request)} if the confirmacionEntregaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the confirmacionEntregaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the confirmacionEntregaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfirmacionEntregaDTO> partialUpdateConfirmacionEntrega(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConfirmacionEntregaDTO confirmacionEntregaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ConfirmacionEntrega partially : {}, {}", id, confirmacionEntregaDTO);
        if (confirmacionEntregaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confirmacionEntregaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confirmacionEntregaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfirmacionEntregaDTO> result = confirmacionEntregaService.partialUpdate(confirmacionEntregaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, confirmacionEntregaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /confirmacion-entregas} : get all the confirmacionEntregas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of confirmacionEntregas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConfirmacionEntregaDTO>> getAllConfirmacionEntregas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ConfirmacionEntregas");
        Page<ConfirmacionEntregaDTO> page = confirmacionEntregaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /confirmacion-entregas/:id} : get the "id" confirmacionEntrega.
     *
     * @param id the id of the confirmacionEntregaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the confirmacionEntregaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConfirmacionEntregaDTO> getConfirmacionEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ConfirmacionEntrega : {}", id);
        Optional<ConfirmacionEntregaDTO> confirmacionEntregaDTO = confirmacionEntregaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(confirmacionEntregaDTO);
    }

    /**
     * {@code DELETE  /confirmacion-entregas/:id} : delete the "id" confirmacionEntrega.
     *
     * @param id the id of the confirmacionEntregaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfirmacionEntrega(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ConfirmacionEntrega : {}", id);
        confirmacionEntregaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
