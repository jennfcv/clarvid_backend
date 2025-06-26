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

@RestController
@RequestMapping("/api/repartidores")
public class RepartidorResource {

    private final Logger log = LoggerFactory.getLogger(RepartidorResource.class);
    private static final String ENTITY_NAME = "repartidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepartidorService repartidorService;
    private final RepartidorRepository repartidorRepository;

    public RepartidorResource(
        RepartidorService repartidorService,
        RepartidorRepository repartidorRepository
    ) {
        this.repartidorService = repartidorService;
        this.repartidorRepository = repartidorRepository;
    }

    @PostMapping("")
    public ResponseEntity<RepartidorDTO> createRepartidor(@Valid @RequestBody RepartidorDTO repartidorDTO) throws URISyntaxException {
        log.debug("REST request to save Repartidor : {}", repartidorDTO);
        if (repartidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new repartidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepartidorDTO result = repartidorService.save(repartidorDTO);
        return ResponseEntity
            .created(new URI("/api/repartidores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepartidorDTO> updateRepartidor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RepartidorDTO repartidorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Repartidor : {}, {}", id, repartidorDTO);
        if (repartidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, repartidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!repartidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RepartidorDTO result = repartidorService.update(repartidorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, repartidorDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RepartidorDTO> partialUpdateRepartidor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RepartidorDTO repartidorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Repartidor partially : {}, {}", id, repartidorDTO);
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
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, repartidorDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RepartidorDTO>> getAllRepartidores(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Repartidores");
        Page<RepartidorDTO> page = repartidorService.findAllWithEagerRelationships(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepartidorDTO> getRepartidor(@PathVariable Long id) {
        log.debug("REST request to get Repartidor : {}", id);
        Optional<RepartidorDTO> repartidorDTO = repartidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(repartidorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepartidor(@PathVariable Long id) {
        log.debug("REST request to delete Repartidor : {}", id);
        repartidorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
