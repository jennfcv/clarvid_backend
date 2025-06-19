package com.paqueteria.service;

import com.paqueteria.domain.ZonaEntrega;
import com.paqueteria.repository.ZonaEntregaRepository;
import com.paqueteria.service.dto.ZonaEntregaDTO;
import com.paqueteria.service.mapper.ZonaEntregaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.ZonaEntrega}.
 */
@Service
@Transactional
public class ZonaEntregaService {

    private static final Logger LOG = LoggerFactory.getLogger(ZonaEntregaService.class);

    private final ZonaEntregaRepository zonaEntregaRepository;

    private final ZonaEntregaMapper zonaEntregaMapper;

    public ZonaEntregaService(ZonaEntregaRepository zonaEntregaRepository, ZonaEntregaMapper zonaEntregaMapper) {
        this.zonaEntregaRepository = zonaEntregaRepository;
        this.zonaEntregaMapper = zonaEntregaMapper;
    }

    /**
     * Save a zonaEntrega.
     *
     * @param zonaEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public ZonaEntregaDTO save(ZonaEntregaDTO zonaEntregaDTO) {
        LOG.debug("Request to save ZonaEntrega : {}", zonaEntregaDTO);
        ZonaEntrega zonaEntrega = zonaEntregaMapper.toEntity(zonaEntregaDTO);
        zonaEntrega = zonaEntregaRepository.save(zonaEntrega);
        return zonaEntregaMapper.toDto(zonaEntrega);
    }

    /**
     * Update a zonaEntrega.
     *
     * @param zonaEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public ZonaEntregaDTO update(ZonaEntregaDTO zonaEntregaDTO) {
        LOG.debug("Request to update ZonaEntrega : {}", zonaEntregaDTO);
        ZonaEntrega zonaEntrega = zonaEntregaMapper.toEntity(zonaEntregaDTO);
        zonaEntrega = zonaEntregaRepository.save(zonaEntrega);
        return zonaEntregaMapper.toDto(zonaEntrega);
    }

    /**
     * Partially update a zonaEntrega.
     *
     * @param zonaEntregaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ZonaEntregaDTO> partialUpdate(ZonaEntregaDTO zonaEntregaDTO) {
        LOG.debug("Request to partially update ZonaEntrega : {}", zonaEntregaDTO);

        return zonaEntregaRepository
            .findById(zonaEntregaDTO.getId())
            .map(existingZonaEntrega -> {
                zonaEntregaMapper.partialUpdate(existingZonaEntrega, zonaEntregaDTO);

                return existingZonaEntrega;
            })
            .map(zonaEntregaRepository::save)
            .map(zonaEntregaMapper::toDto);
    }

    /**
     * Get all the zonaEntregas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ZonaEntregaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ZonaEntregas");
        return zonaEntregaRepository.findAll(pageable).map(zonaEntregaMapper::toDto);
    }

    /**
     * Get one zonaEntrega by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ZonaEntregaDTO> findOne(Long id) {
        LOG.debug("Request to get ZonaEntrega : {}", id);
        return zonaEntregaRepository.findById(id).map(zonaEntregaMapper::toDto);
    }

    /**
     * Delete the zonaEntrega by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ZonaEntrega : {}", id);
        zonaEntregaRepository.deleteById(id);
    }
}
