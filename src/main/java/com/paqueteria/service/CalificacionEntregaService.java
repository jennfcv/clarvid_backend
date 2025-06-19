package com.paqueteria.service;

import com.paqueteria.domain.CalificacionEntrega;
import com.paqueteria.repository.CalificacionEntregaRepository;
import com.paqueteria.service.dto.CalificacionEntregaDTO;
import com.paqueteria.service.mapper.CalificacionEntregaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.CalificacionEntrega}.
 */
@Service
@Transactional
public class CalificacionEntregaService {

    private static final Logger LOG = LoggerFactory.getLogger(CalificacionEntregaService.class);

    private final CalificacionEntregaRepository calificacionEntregaRepository;

    private final CalificacionEntregaMapper calificacionEntregaMapper;

    public CalificacionEntregaService(
        CalificacionEntregaRepository calificacionEntregaRepository,
        CalificacionEntregaMapper calificacionEntregaMapper
    ) {
        this.calificacionEntregaRepository = calificacionEntregaRepository;
        this.calificacionEntregaMapper = calificacionEntregaMapper;
    }

    /**
     * Save a calificacionEntrega.
     *
     * @param calificacionEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public CalificacionEntregaDTO save(CalificacionEntregaDTO calificacionEntregaDTO) {
        LOG.debug("Request to save CalificacionEntrega : {}", calificacionEntregaDTO);
        CalificacionEntrega calificacionEntrega = calificacionEntregaMapper.toEntity(calificacionEntregaDTO);
        calificacionEntrega = calificacionEntregaRepository.save(calificacionEntrega);
        return calificacionEntregaMapper.toDto(calificacionEntrega);
    }

    /**
     * Update a calificacionEntrega.
     *
     * @param calificacionEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public CalificacionEntregaDTO update(CalificacionEntregaDTO calificacionEntregaDTO) {
        LOG.debug("Request to update CalificacionEntrega : {}", calificacionEntregaDTO);
        CalificacionEntrega calificacionEntrega = calificacionEntregaMapper.toEntity(calificacionEntregaDTO);
        calificacionEntrega = calificacionEntregaRepository.save(calificacionEntrega);
        return calificacionEntregaMapper.toDto(calificacionEntrega);
    }

    /**
     * Partially update a calificacionEntrega.
     *
     * @param calificacionEntregaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CalificacionEntregaDTO> partialUpdate(CalificacionEntregaDTO calificacionEntregaDTO) {
        LOG.debug("Request to partially update CalificacionEntrega : {}", calificacionEntregaDTO);

        return calificacionEntregaRepository
            .findById(calificacionEntregaDTO.getId())
            .map(existingCalificacionEntrega -> {
                calificacionEntregaMapper.partialUpdate(existingCalificacionEntrega, calificacionEntregaDTO);

                return existingCalificacionEntrega;
            })
            .map(calificacionEntregaRepository::save)
            .map(calificacionEntregaMapper::toDto);
    }

    /**
     * Get all the calificacionEntregas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CalificacionEntregaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CalificacionEntregas");
        return calificacionEntregaRepository.findAll(pageable).map(calificacionEntregaMapper::toDto);
    }

    /**
     * Get one calificacionEntrega by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CalificacionEntregaDTO> findOne(Long id) {
        LOG.debug("Request to get CalificacionEntrega : {}", id);
        return calificacionEntregaRepository.findById(id).map(calificacionEntregaMapper::toDto);
    }

    /**
     * Delete the calificacionEntrega by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete CalificacionEntrega : {}", id);
        calificacionEntregaRepository.deleteById(id);
    }
}
