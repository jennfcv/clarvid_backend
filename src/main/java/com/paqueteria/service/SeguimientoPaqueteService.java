package com.paqueteria.service;

import com.paqueteria.domain.SeguimientoPaquete;
import com.paqueteria.repository.SeguimientoPaqueteRepository;
import com.paqueteria.service.dto.SeguimientoPaqueteDTO;
import com.paqueteria.service.mapper.SeguimientoPaqueteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.SeguimientoPaquete}.
 */
@Service
@Transactional
public class SeguimientoPaqueteService {

    private static final Logger LOG = LoggerFactory.getLogger(SeguimientoPaqueteService.class);

    private final SeguimientoPaqueteRepository seguimientoPaqueteRepository;

    private final SeguimientoPaqueteMapper seguimientoPaqueteMapper;

    public SeguimientoPaqueteService(
        SeguimientoPaqueteRepository seguimientoPaqueteRepository,
        SeguimientoPaqueteMapper seguimientoPaqueteMapper
    ) {
        this.seguimientoPaqueteRepository = seguimientoPaqueteRepository;
        this.seguimientoPaqueteMapper = seguimientoPaqueteMapper;
    }

    /**
     * Save a seguimientoPaquete.
     *
     * @param seguimientoPaqueteDTO the entity to save.
     * @return the persisted entity.
     */
    public SeguimientoPaqueteDTO save(SeguimientoPaqueteDTO seguimientoPaqueteDTO) {
        LOG.debug("Request to save SeguimientoPaquete : {}", seguimientoPaqueteDTO);
        SeguimientoPaquete seguimientoPaquete = seguimientoPaqueteMapper.toEntity(seguimientoPaqueteDTO);
        seguimientoPaquete = seguimientoPaqueteRepository.save(seguimientoPaquete);
        return seguimientoPaqueteMapper.toDto(seguimientoPaquete);
    }

    /**
     * Update a seguimientoPaquete.
     *
     * @param seguimientoPaqueteDTO the entity to save.
     * @return the persisted entity.
     */
    public SeguimientoPaqueteDTO update(SeguimientoPaqueteDTO seguimientoPaqueteDTO) {
        LOG.debug("Request to update SeguimientoPaquete : {}", seguimientoPaqueteDTO);
        SeguimientoPaquete seguimientoPaquete = seguimientoPaqueteMapper.toEntity(seguimientoPaqueteDTO);
        seguimientoPaquete = seguimientoPaqueteRepository.save(seguimientoPaquete);
        return seguimientoPaqueteMapper.toDto(seguimientoPaquete);
    }

    /**
     * Partially update a seguimientoPaquete.
     *
     * @param seguimientoPaqueteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SeguimientoPaqueteDTO> partialUpdate(SeguimientoPaqueteDTO seguimientoPaqueteDTO) {
        LOG.debug("Request to partially update SeguimientoPaquete : {}", seguimientoPaqueteDTO);

        return seguimientoPaqueteRepository
            .findById(seguimientoPaqueteDTO.getId())
            .map(existingSeguimientoPaquete -> {
                seguimientoPaqueteMapper.partialUpdate(existingSeguimientoPaquete, seguimientoPaqueteDTO);

                return existingSeguimientoPaquete;
            })
            .map(seguimientoPaqueteRepository::save)
            .map(seguimientoPaqueteMapper::toDto);
    }

    /**
     * Get all the seguimientoPaquetes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SeguimientoPaqueteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SeguimientoPaquetes");
        return seguimientoPaqueteRepository.findAll(pageable).map(seguimientoPaqueteMapper::toDto);
    }

    /**
     * Get one seguimientoPaquete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SeguimientoPaqueteDTO> findOne(Long id) {
        LOG.debug("Request to get SeguimientoPaquete : {}", id);
        return seguimientoPaqueteRepository.findById(id).map(seguimientoPaqueteMapper::toDto);
    }

    /**
     * Delete the seguimientoPaquete by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SeguimientoPaquete : {}", id);
        seguimientoPaqueteRepository.deleteById(id);
    }
}
