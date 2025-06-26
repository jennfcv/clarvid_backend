package com.paqueteria.web.rest;

import com.paqueteria.repository.PaqueteRepository;
import com.paqueteria.service.MailService;
import com.paqueteria.service.NotificationService;
import com.paqueteria.service.PaqueteService;
import com.paqueteria.service.dto.PaqueteDTO;
import com.paqueteria.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteResource {

    private final Logger log = LoggerFactory.getLogger(PaqueteResource.class);
    private static final String ENTITY_NAME = "paquete";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaqueteService paqueteService;
    private final PaqueteRepository paqueteRepository;
    private final MailService mailService;
    private final NotificationService notificationService;

    public PaqueteResource(
        PaqueteService paqueteService,
        PaqueteRepository paqueteRepository,
        MailService mailService,
        NotificationService notificationService
    ) {
        this.paqueteService = paqueteService;
        this.paqueteRepository = paqueteRepository;
        this.mailService = mailService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<PaqueteDTO> createPaquete(@Valid @RequestBody PaqueteDTO paqueteDTO) throws URISyntaxException {
        log.debug("REST request to save Paquete : {}", paqueteDTO);

        if (paqueteDTO.getId() != null) {
            throw new BadRequestAlertException("A new paquete cannot already have an ID", ENTITY_NAME, "idexists");
        }

        PaqueteDTO result = paqueteService.save(paqueteDTO);

        // Notificaciones al destinatario
        if (result.getDestinatario() != null) {
            String correo = result.getDestinatario().getEmail();
            String telefono = result.getDestinatario().getTelefono();
            String mensaje = "📦 Su paquete fue registrado correctamente. Código de seguimiento: " + result.getCodigoSeguimiento();

            if (correo != null && !correo.isBlank()) {
                mailService.sendEmail(correo, "Registro de Paquete", mensaje, false, false);
            }

            if (telefono != null && !telefono.isBlank()) {
                notificationService.enviarWhatsApp(telefono, mensaje);
                notificationService.enviarSMS(telefono, mensaje);
            }
        }

        return ResponseEntity
            .created(new URI("/api/paquetes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaqueteDTO> updatePaquete(
        @PathVariable final Long id,
        @Valid @RequestBody PaqueteDTO paqueteDTO
    ) {
        log.debug("REST request to update Paquete : {}, {}", id, paqueteDTO);
        if (paqueteDTO.getId() == null || !Objects.equals(id, paqueteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paqueteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaqueteDTO result = paqueteService.update(paqueteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paqueteDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaqueteDTO> getPaquete(@PathVariable Long id) {
        Optional<PaqueteDTO> paqueteDTO = paqueteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paqueteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaquete(@PathVariable Long id) {
        log.debug("REST request to delete Paquete : {}", id);
        paqueteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
