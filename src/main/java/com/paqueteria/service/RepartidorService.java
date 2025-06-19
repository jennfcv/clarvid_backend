package com.paqueteria.service;

import com.paqueteria.domain.Repartidor;
import com.paqueteria.repository.RepartidorRepository;
import com.paqueteria.service.dto.RepartidorDTO;
import com.paqueteria.service.mapper.RepartidorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.Repartidor}.
 */
@Service
@Transactional
public class RepartidorService {

    private static final Logger LOG = LoggerFactory.getLogger(RepartidorService.class);

    private final RepartidorRepository repartidorRepository;

    private final RepartidorMapper repartidorMapper;

    public RepartidorService(RepartidorRepository repartidorRepository, RepartidorMapper repartidorMapper) {
        this.repartidorRepository = repartidorRepository;
        this.repartidorMapper = repartidorMapper;
    }

    /**
     * Save a repartidor.
     *
     * @param repartidorDTO the entity to save.
     * @return the persisted entity.
     */
    public RepartidorDTO save(RepartidorDTO repartidorDTO) {
        LOG.debug("Request to save Repartidor : {}", repartidorDTO);
        Repartidor repartidor = repartidorMapper.toEntity(repartidorDTO);
        repartidor = repartidorRepository.save(repartidor);
        return repartidorMapper.toDto(repartidor);
    }

    /**
     * Update a repartidor.
     *
     * @param repartidorDTO the entity to save.
     * @return the persisted entity.
     */
    public RepartidorDTO update(RepartidorDTO repartidorDTO) {
        LOG.debug("Request to update Repartidor : {}", repartidorDTO);
        Repartidor repartidor = repartidorMapper.toEntity(repartidorDTO);
        repartidor = repartidorRepository.save(repartidor);
        return repartidorMapper.toDto(repartidor);
    }

    /**
     * Partially update a repartidor.
     *
     * @param repartidorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RepartidorDTO> partialUpdate(RepartidorDTO repartidorDTO) {
        LOG.debug("Request to partially update Repartidor : {}", repartidorDTO);

        return repartidorRepository
            .findById(repartidorDTO.getId())
            .map(existingRepartidor -> {
                repartidorMapper.partialUpdate(existingRepartidor, repartidorDTO);

                return existingRepartidor;
            })
            .map(repartidorRepository::save)
            .map(repartidorMapper::toDto);
    }

    /**
     * Get all the repartidors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RepartidorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Repartidors");
        return repartidorRepository.findAll(pageable).map(repartidorMapper::toDto);
    }

    /**
     * Get all the repartidors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RepartidorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return repartidorRepository.findAllWithEagerRelationships(pageable).map(repartidorMapper::toDto);
    }

    /**
     * Get one repartidor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RepartidorDTO> findOne(Long id) {
        LOG.debug("Request to get Repartidor : {}", id);
        return repartidorRepository.findOneWithEagerRelationships(id).map(repartidorMapper::toDto);
    }

    /**
     * Delete the repartidor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Repartidor : {}", id);
        repartidorRepository.deleteById(id);
    }
}
