package com.paqueteria.service;

import com.paqueteria.domain.Paquete;
import com.paqueteria.domain.PersonaPaquete;
import com.paqueteria.repository.PaqueteRepository;
import com.paqueteria.repository.PersonaPaqueteRepository;
import com.paqueteria.service.dto.PaqueteDTO;
import com.paqueteria.service.mapper.PaqueteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.Paquete}.
 */
@Service
@Transactional
public class PaqueteService {

    private final Logger log = LoggerFactory.getLogger(PaqueteService.class);

    private final PaqueteRepository paqueteRepository;
    private final PaqueteMapper paqueteMapper;
    private final PersonaPaqueteRepository personaPaqueteRepository;

    public PaqueteService(
        PaqueteRepository paqueteRepository,
        PaqueteMapper paqueteMapper,
        PersonaPaqueteRepository personaPaqueteRepository
    ) {
        this.paqueteRepository = paqueteRepository;
        this.paqueteMapper = paqueteMapper;
        this.personaPaqueteRepository = personaPaqueteRepository;
    }

    /**
     * Save a paquete.
     *
     * @param paqueteDTO the entity to save.
     * @return the persisted entity.
     */
    public PaqueteDTO save(PaqueteDTO paqueteDTO) {
        log.debug("Request to save Paquete : {}", paqueteDTO);

        Paquete paquete = paqueteMapper.toEntity(paqueteDTO);

        // Guardar remitente si es nuevo
        if (paquete.getRemitente() != null && paquete.getRemitente().getId() == null) {
            PersonaPaquete remitente = personaPaqueteRepository.save(paquete.getRemitente());
            paquete.setRemitente(remitente);
        }

        // Guardar destinatario si es nuevo
        if (paquete.getDestinatario() != null && paquete.getDestinatario().getId() == null) {
            PersonaPaquete destinatario = personaPaqueteRepository.save(paquete.getDestinatario());
            paquete.setDestinatario(destinatario);
        }

        paquete = paqueteRepository.save(paquete);
        return paqueteMapper.toDto(paquete);
    }

    /**
     * Update a paquete.
     *
     * @param paqueteDTO the entity to update.
     * @return the persisted entity.
     */
    public PaqueteDTO update(PaqueteDTO paqueteDTO) {
        log.debug("Request to update Paquete : {}", paqueteDTO);
        Paquete paquete = paqueteMapper.toEntity(paqueteDTO);
        paquete = paqueteRepository.save(paquete);
        return paqueteMapper.toDto(paquete);
    }

    /**
     * Get one paquete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaqueteDTO> findOne(Long id) {
        log.debug("Request to get Paquete : {}", id);
        return paqueteRepository.findById(id).map(paqueteMapper::toDto);
    }

    /**
     * Delete the paquete by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paquete : {}", id);
        paqueteRepository.deleteById(id);
    }

    /**
     * Get all the paquetes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaqueteDTO> findAll(Pageable pageable) {
        return paqueteRepository.findAll(pageable).map(paqueteMapper::toDto);
    }
    public Page<Paquete> findAllWithEagerRelationships(Pageable pageable) {
        return paqueteRepository.findAll(pageable); // sin fetch joins reales
    }

}
