package com.paqueteria.web.rest;

import com.paqueteria.repository.PersonaPaqueteRepository;
import com.paqueteria.service.PersonaPaqueteService;
import com.paqueteria.service.dto.PersonaPaqueteDTO;
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
 * REST controller for managing {@link com.paqueteria.domain.PersonaPaquete}.
 */
@RestController
@RequestMapping("/api/persona-paquetes")
public class PersonaPaqueteResource {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaPaqueteResource.class);

    private static final String ENTITY_NAME = "personaPaquete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonaPaqueteService personaPaqueteService;

    private final PersonaPaqueteRepository personaPaqueteRepository;

    public PersonaPaqueteResource(PersonaPaqueteService personaPaqueteService, PersonaPaqueteRepository personaPaqueteRepository) {
        this.personaPaqueteService = personaPaqueteService;
        this.personaPaqueteRepository = personaPaqueteRepository;
    }

    /**
     * {@code POST  /persona-paquetes} : Create a new personaPaquete.
     *
     * @param personaPaqueteDTO the personaPaqueteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personaPaqueteDTO, or with status {@code 400 (Bad Request)} if the personaPaquete has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PersonaPaqueteDTO> createPersonaPaquete(@Valid @RequestBody PersonaPaqueteDTO personaPaqueteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PersonaPaquete : {}", personaPaqueteDTO);
        if (personaPaqueteDTO.getId() != null) {
            throw new BadRequestAlertException("A new personaPaquete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        personaPaqueteDTO = personaPaqueteService.save(personaPaqueteDTO);
        return ResponseEntity.created(new URI("/api/persona-paquetes/" + personaPaqueteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, personaPaqueteDTO.getId().toString()))
            .body(personaPaqueteDTO);
    }

    /**
     * {@code PUT  /persona-paquetes/:id} : Updates an existing personaPaquete.
     *
     * @param id the id of the personaPaqueteDTO to save.
     * @param personaPaqueteDTO the personaPaqueteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaPaqueteDTO,
     * or with status {@code 400 (Bad Request)} if the personaPaqueteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personaPaqueteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonaPaqueteDTO> updatePersonaPaquete(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonaPaqueteDTO personaPaqueteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PersonaPaquete : {}, {}", id, personaPaqueteDTO);
        if (personaPaqueteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaPaqueteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaPaqueteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        personaPaqueteDTO = personaPaqueteService.update(personaPaqueteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personaPaqueteDTO.getId().toString()))
            .body(personaPaqueteDTO);
    }

    /**
     * {@code PATCH  /persona-paquetes/:id} : Partial updates given fields of an existing personaPaquete, field will ignore if it is null
     *
     * @param id the id of the personaPaqueteDTO to save.
     * @param personaPaqueteDTO the personaPaqueteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaPaqueteDTO,
     * or with status {@code 400 (Bad Request)} if the personaPaqueteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personaPaqueteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personaPaqueteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonaPaqueteDTO> partialUpdatePersonaPaquete(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonaPaqueteDTO personaPaqueteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PersonaPaquete partially : {}, {}", id, personaPaqueteDTO);
        if (personaPaqueteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaPaqueteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaPaqueteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonaPaqueteDTO> result = personaPaqueteService.partialUpdate(personaPaqueteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personaPaqueteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /persona-paquetes} : get all the personaPaquetes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personaPaquetes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PersonaPaqueteDTO>> getAllPersonaPaquetes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PersonaPaquetes");
        Page<PersonaPaqueteDTO> page = personaPaqueteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /persona-paquetes/:id} : get the "id" personaPaquete.
     *
     * @param id the id of the personaPaqueteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personaPaqueteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonaPaqueteDTO> getPersonaPaquete(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PersonaPaquete : {}", id);
        Optional<PersonaPaqueteDTO> personaPaqueteDTO = personaPaqueteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personaPaqueteDTO);
    }

    /**
     * {@code DELETE  /persona-paquetes/:id} : delete the "id" personaPaquete.
     *
     * @param id the id of the personaPaqueteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonaPaquete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PersonaPaquete : {}", id);
        personaPaqueteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
