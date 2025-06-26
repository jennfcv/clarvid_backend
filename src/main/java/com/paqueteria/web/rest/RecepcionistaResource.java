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

@RestController
@RequestMapping("/api/recepcionistas")
public class RecepcionistaResource {

    private final Logger log = LoggerFactory.getLogger(RecepcionistaResource.class);
    private static final String ENTITY_NAME = "recepcionista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecepcionistaService recepcionistaService;
    private final RecepcionistaRepository recepcionistaRepository;

    public RecepcionistaResource(
        RecepcionistaService recepcionistaService,
        RecepcionistaRepository recepcionistaRepository
    ) {
        this.recepcionistaService = recepcionistaService;
        this.recepcionistaRepository = recepcionistaRepository;
    }

    @PostMapping("")
    public ResponseEntity<RecepcionistaDTO> createRecepcionista(@Valid @RequestBody RecepcionistaDTO recepcionistaDTO)
        throws URISyntaxException {
        log.debug("REST request to save Recepcionista : {}", recepcionistaDTO);
        if (recepcionistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new recepcionista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecepcionistaDTO result = recepcionistaService.save(recepcionistaDTO);
        return ResponseEntity
            .created(new URI("/api/recepcionistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> updateRecepcionista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecepcionistaDTO recepcionistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Recepcionista : {}, {}", id, recepcionistaDTO);
        if (recepcionistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recepcionistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!recepcionistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecepcionistaDTO result = recepcionistaService.update(recepcionistaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recepcionistaDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecepcionistaDTO> partialUpdateRecepcionista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecepcionistaDTO recepcionistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recepcionista partially : {}, {}", id, recepcionistaDTO);
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
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recepcionistaDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RecepcionistaDTO>> getAllRecepcionistas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Recepcionistas");
        Page<RecepcionistaDTO> page = recepcionistaService.findAllWithEagerRelationships(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionistaDTO> getRecepcionista(@PathVariable Long id) {
        log.debug("REST request to get Recepcionista : {}", id);
        Optional<RecepcionistaDTO> recepcionistaDTO = recepcionistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recepcionistaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecepcionista(@PathVariable Long id) {
        log.debug("REST request to delete Recepcionista : {}", id);
        recepcionistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
