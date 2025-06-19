package com.paqueteria.service;

import com.paqueteria.domain.Recepcionista;
import com.paqueteria.repository.RecepcionistaRepository;
import com.paqueteria.service.dto.RecepcionistaDTO;
import com.paqueteria.service.mapper.RecepcionistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.Recepcionista}.
 */
@Service
@Transactional
public class RecepcionistaService {

    private static final Logger LOG = LoggerFactory.getLogger(RecepcionistaService.class);

    private final RecepcionistaRepository recepcionistaRepository;

    private final RecepcionistaMapper recepcionistaMapper;

    public RecepcionistaService(RecepcionistaRepository recepcionistaRepository, RecepcionistaMapper recepcionistaMapper) {
        this.recepcionistaRepository = recepcionistaRepository;
        this.recepcionistaMapper = recepcionistaMapper;
    }

    /**
     * Save a recepcionista.
     *
     * @param recepcionistaDTO the entity to save.
     * @return the persisted entity.
     */
    public RecepcionistaDTO save(RecepcionistaDTO recepcionistaDTO) {
        LOG.debug("Request to save Recepcionista : {}", recepcionistaDTO);
        Recepcionista recepcionista = recepcionistaMapper.toEntity(recepcionistaDTO);
        recepcionista = recepcionistaRepository.save(recepcionista);
        return recepcionistaMapper.toDto(recepcionista);
    }

    /**
     * Update a recepcionista.
     *
     * @param recepcionistaDTO the entity to save.
     * @return the persisted entity.
     */
    public RecepcionistaDTO update(RecepcionistaDTO recepcionistaDTO) {
        LOG.debug("Request to update Recepcionista : {}", recepcionistaDTO);
        Recepcionista recepcionista = recepcionistaMapper.toEntity(recepcionistaDTO);
        recepcionista = recepcionistaRepository.save(recepcionista);
        return recepcionistaMapper.toDto(recepcionista);
    }

    /**
     * Partially update a recepcionista.
     *
     * @param recepcionistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RecepcionistaDTO> partialUpdate(RecepcionistaDTO recepcionistaDTO) {
        LOG.debug("Request to partially update Recepcionista : {}", recepcionistaDTO);

        return recepcionistaRepository
            .findById(recepcionistaDTO.getId())
            .map(existingRecepcionista -> {
                recepcionistaMapper.partialUpdate(existingRecepcionista, recepcionistaDTO);

                return existingRecepcionista;
            })
            .map(recepcionistaRepository::save)
            .map(recepcionistaMapper::toDto);
    }

    /**
     * Get all the recepcionistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecepcionistaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Recepcionistas");
        return recepcionistaRepository.findAll(pageable).map(recepcionistaMapper::toDto);
    }

    /**
     * Get all the recepcionistas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RecepcionistaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return recepcionistaRepository.findAllWithEagerRelationships(pageable).map(recepcionistaMapper::toDto);
    }

    /**
     * Get one recepcionista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecepcionistaDTO> findOne(Long id) {
        LOG.debug("Request to get Recepcionista : {}", id);
        return recepcionistaRepository.findOneWithEagerRelationships(id).map(recepcionistaMapper::toDto);
    }

    /**
     * Delete the recepcionista by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Recepcionista : {}", id);
        recepcionistaRepository.deleteById(id);
    }
}
