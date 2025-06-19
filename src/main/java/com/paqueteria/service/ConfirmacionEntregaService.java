package com.paqueteria.service;

import com.paqueteria.domain.ConfirmacionEntrega;
import com.paqueteria.repository.ConfirmacionEntregaRepository;
import com.paqueteria.service.dto.ConfirmacionEntregaDTO;
import com.paqueteria.service.mapper.ConfirmacionEntregaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.ConfirmacionEntrega}.
 */
@Service
@Transactional
public class ConfirmacionEntregaService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmacionEntregaService.class);

    private final ConfirmacionEntregaRepository confirmacionEntregaRepository;

    private final ConfirmacionEntregaMapper confirmacionEntregaMapper;

    public ConfirmacionEntregaService(
        ConfirmacionEntregaRepository confirmacionEntregaRepository,
        ConfirmacionEntregaMapper confirmacionEntregaMapper
    ) {
        this.confirmacionEntregaRepository = confirmacionEntregaRepository;
        this.confirmacionEntregaMapper = confirmacionEntregaMapper;
    }

    /**
     * Save a confirmacionEntrega.
     *
     * @param confirmacionEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfirmacionEntregaDTO save(ConfirmacionEntregaDTO confirmacionEntregaDTO) {
        LOG.debug("Request to save ConfirmacionEntrega : {}", confirmacionEntregaDTO);
        ConfirmacionEntrega confirmacionEntrega = confirmacionEntregaMapper.toEntity(confirmacionEntregaDTO);
        confirmacionEntrega = confirmacionEntregaRepository.save(confirmacionEntrega);
        return confirmacionEntregaMapper.toDto(confirmacionEntrega);
    }

    /**
     * Update a confirmacionEntrega.
     *
     * @param confirmacionEntregaDTO the entity to save.
     * @return the persisted entity.
     */
    public ConfirmacionEntregaDTO update(ConfirmacionEntregaDTO confirmacionEntregaDTO) {
        LOG.debug("Request to update ConfirmacionEntrega : {}", confirmacionEntregaDTO);
        ConfirmacionEntrega confirmacionEntrega = confirmacionEntregaMapper.toEntity(confirmacionEntregaDTO);
        confirmacionEntrega = confirmacionEntregaRepository.save(confirmacionEntrega);
        return confirmacionEntregaMapper.toDto(confirmacionEntrega);
    }

    /**
     * Partially update a confirmacionEntrega.
     *
     * @param confirmacionEntregaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConfirmacionEntregaDTO> partialUpdate(ConfirmacionEntregaDTO confirmacionEntregaDTO) {
        LOG.debug("Request to partially update ConfirmacionEntrega : {}", confirmacionEntregaDTO);

        return confirmacionEntregaRepository
            .findById(confirmacionEntregaDTO.getId())
            .map(existingConfirmacionEntrega -> {
                confirmacionEntregaMapper.partialUpdate(existingConfirmacionEntrega, confirmacionEntregaDTO);

                return existingConfirmacionEntrega;
            })
            .map(confirmacionEntregaRepository::save)
            .map(confirmacionEntregaMapper::toDto);
    }

    /**
     * Get all the confirmacionEntregas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfirmacionEntregaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ConfirmacionEntregas");
        return confirmacionEntregaRepository.findAll(pageable).map(confirmacionEntregaMapper::toDto);
    }

    /**
     * Get one confirmacionEntrega by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfirmacionEntregaDTO> findOne(Long id) {
        LOG.debug("Request to get ConfirmacionEntrega : {}", id);
        return confirmacionEntregaRepository.findById(id).map(confirmacionEntregaMapper::toDto);
    }

    /**
     * Delete the confirmacionEntrega by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ConfirmacionEntrega : {}", id);
        confirmacionEntregaRepository.deleteById(id);
    }
}
