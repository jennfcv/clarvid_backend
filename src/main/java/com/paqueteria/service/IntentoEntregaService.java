package com.paqueteria.service;

import com.paqueteria.domain.IntentoEntrega;
import com.paqueteria.repository.IntentoEntregaRepository;
import com.paqueteria.service.dto.IntentoEntregaDTO;
import com.paqueteria.service.mapper.IntentoEntregaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.IntentoEntrega}.
 */
@Service
@Transactional
public class IntentoEntregaService {

    private static final Logger LOG = LoggerFactory.getLogger(IntentoEntregaService.class);

    private final IntentoEntregaRepository intentoEntregaRepository;

    private final IntentoEntregaMapper intentoEntregaMapper;

    public IntentoEntregaService(IntentoEntregaRepository intentoEntregaRepository, IntentoEntregaMapper intentoEntregaMapper) {
        this.intentoEntregaRepository = intentoEntregaRepository;
        this.intentoEntregaMapper = intentoEntregaMapper;
    }

    /**
     * Save a intentoEntrega.
     *
     * @param intentoEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public IntentoEntregaDTO save(IntentoEntregaDTO intentoEntregaDTO) {
        LOG.debug("Request to save IntentoEntrega : {}", intentoEntregaDTO);
        IntentoEntrega intentoEntrega = intentoEntregaMapper.toEntity(intentoEntregaDTO);
        intentoEntrega = intentoEntregaRepository.save(intentoEntrega);
        return intentoEntregaMapper.toDto(intentoEntrega);
    }

    /**
     * Update a intentoEntrega.
     *
     * @param intentoEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public IntentoEntregaDTO update(IntentoEntregaDTO intentoEntregaDTO) {
        LOG.debug("Request to update IntentoEntrega : {}", intentoEntregaDTO);
        IntentoEntrega intentoEntrega = intentoEntregaMapper.toEntity(intentoEntregaDTO);
        intentoEntrega = intentoEntregaRepository.save(intentoEntrega);
        return intentoEntregaMapper.toDto(intentoEntrega);
    }

    /**
     * Partially update a intentoEntrega.
     *
     * @param intentoEntregaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IntentoEntregaDTO> partialUpdate(IntentoEntregaDTO intentoEntregaDTO) {
        LOG.debug("Request to partially update IntentoEntrega : {}", intentoEntregaDTO);

        return intentoEntregaRepository
            .findById(intentoEntregaDTO.getId())
            .map(existingIntentoEntrega -> {
                intentoEntregaMapper.partialUpdate(existingIntentoEntrega, intentoEntregaDTO);

                return existingIntentoEntrega;
            })
            .map(intentoEntregaRepository::save)
            .map(intentoEntregaMapper::toDto);
    }

    /**
     * Get all the intentoEntregas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IntentoEntregaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all IntentoEntregas");
        return intentoEntregaRepository.findAll(pageable).map(intentoEntregaMapper::toDto);
    }

    /**
     * Get one intentoEntrega by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IntentoEntregaDTO> findOne(Long id) {
        LOG.debug("Request to get IntentoEntrega : {}", id);
        return intentoEntregaRepository.findById(id).map(intentoEntregaMapper::toDto);
    }

    /**
     * Delete the intentoEntrega by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete IntentoEntrega : {}", id);
        intentoEntregaRepository.deleteById(id);
    }
}
