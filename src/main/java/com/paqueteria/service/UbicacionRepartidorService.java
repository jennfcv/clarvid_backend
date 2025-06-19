package com.paqueteria.service;

import com.paqueteria.domain.UbicacionRepartidor;
import com.paqueteria.repository.UbicacionRepartidorRepository;
import com.paqueteria.service.dto.UbicacionRepartidorDTO;
import com.paqueteria.service.mapper.UbicacionRepartidorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.UbicacionRepartidor}.
 */
@Service
@Transactional
public class UbicacionRepartidorService {

    private static final Logger LOG = LoggerFactory.getLogger(UbicacionRepartidorService.class);

    private final UbicacionRepartidorRepository ubicacionRepartidorRepository;

    private final UbicacionRepartidorMapper ubicacionRepartidorMapper;

    public UbicacionRepartidorService(
        UbicacionRepartidorRepository ubicacionRepartidorRepository,
        UbicacionRepartidorMapper ubicacionRepartidorMapper
    ) {
        this.ubicacionRepartidorRepository = ubicacionRepartidorRepository;
        this.ubicacionRepartidorMapper = ubicacionRepartidorMapper;
    }

    /**
     * Save a ubicacionRepartidor.
     *
     * @param ubicacionRepartidorDTO the entity to save.
     * @return the persisted entity.
     */
    public UbicacionRepartidorDTO save(UbicacionRepartidorDTO ubicacionRepartidorDTO) {
        LOG.debug("Request to save UbicacionRepartidor : {}", ubicacionRepartidorDTO);
        UbicacionRepartidor ubicacionRepartidor = ubicacionRepartidorMapper.toEntity(ubicacionRepartidorDTO);
        ubicacionRepartidor = ubicacionRepartidorRepository.save(ubicacionRepartidor);
        return ubicacionRepartidorMapper.toDto(ubicacionRepartidor);
    }

    /**
     * Update a ubicacionRepartidor.
     *
     * @param ubicacionRepartidorDTO the entity to save.
     * @return the persisted entity.
     */
    public UbicacionRepartidorDTO update(UbicacionRepartidorDTO ubicacionRepartidorDTO) {
        LOG.debug("Request to update UbicacionRepartidor : {}", ubicacionRepartidorDTO);
        UbicacionRepartidor ubicacionRepartidor = ubicacionRepartidorMapper.toEntity(ubicacionRepartidorDTO);
        ubicacionRepartidor = ubicacionRepartidorRepository.save(ubicacionRepartidor);
        return ubicacionRepartidorMapper.toDto(ubicacionRepartidor);
    }

    /**
     * Partially update a ubicacionRepartidor.
     *
     * @param ubicacionRepartidorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UbicacionRepartidorDTO> partialUpdate(UbicacionRepartidorDTO ubicacionRepartidorDTO) {
        LOG.debug("Request to partially update UbicacionRepartidor : {}", ubicacionRepartidorDTO);

        return ubicacionRepartidorRepository
            .findById(ubicacionRepartidorDTO.getId())
            .map(existingUbicacionRepartidor -> {
                ubicacionRepartidorMapper.partialUpdate(existingUbicacionRepartidor, ubicacionRepartidorDTO);

                return existingUbicacionRepartidor;
            })
            .map(ubicacionRepartidorRepository::save)
            .map(ubicacionRepartidorMapper::toDto);
    }

    /**
     * Get all the ubicacionRepartidors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UbicacionRepartidorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all UbicacionRepartidors");
        return ubicacionRepartidorRepository.findAll(pageable).map(ubicacionRepartidorMapper::toDto);
    }

    /**
     * Get one ubicacionRepartidor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UbicacionRepartidorDTO> findOne(Long id) {
        LOG.debug("Request to get UbicacionRepartidor : {}", id);
        return ubicacionRepartidorRepository.findById(id).map(ubicacionRepartidorMapper::toDto);
    }

    /**
     * Delete the ubicacionRepartidor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete UbicacionRepartidor : {}", id);
        ubicacionRepartidorRepository.deleteById(id);
    }
}
