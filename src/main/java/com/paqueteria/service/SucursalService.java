package com.paqueteria.service;

import com.paqueteria.domain.Sucursal;
import com.paqueteria.repository.SucursalRepository;
import com.paqueteria.service.dto.SucursalDTO;
import com.paqueteria.service.mapper.SucursalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.Sucursal}.
 */
@Service
@Transactional
public class SucursalService {

    private static final Logger LOG = LoggerFactory.getLogger(SucursalService.class);

    private final SucursalRepository sucursalRepository;

    private final SucursalMapper sucursalMapper;

    public SucursalService(SucursalRepository sucursalRepository, SucursalMapper sucursalMapper) {
        this.sucursalRepository = sucursalRepository;
        this.sucursalMapper = sucursalMapper;
    }

    /**
     * Save a sucursal.
     *
     * @param sucursalDTO the entity to save.
     * @return the persisted entity.
     */
    public SucursalDTO save(SucursalDTO sucursalDTO) {
        LOG.debug("Request to save Sucursal : {}", sucursalDTO);
        Sucursal sucursal = sucursalMapper.toEntity(sucursalDTO);
        sucursal = sucursalRepository.save(sucursal);
        return sucursalMapper.toDto(sucursal);
    }

    /**
     * Update a sucursal.
     *
     * @param sucursalDTO the entity to save.
     * @return the persisted entity.
     */
    public SucursalDTO update(SucursalDTO sucursalDTO) {
        LOG.debug("Request to update Sucursal : {}", sucursalDTO);
        Sucursal sucursal = sucursalMapper.toEntity(sucursalDTO);
        sucursal = sucursalRepository.save(sucursal);
        return sucursalMapper.toDto(sucursal);
    }

    /**
     * Partially update a sucursal.
     *
     * @param sucursalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SucursalDTO> partialUpdate(SucursalDTO sucursalDTO) {
        LOG.debug("Request to partially update Sucursal : {}", sucursalDTO);

        return sucursalRepository
            .findById(sucursalDTO.getId())
            .map(existingSucursal -> {
                sucursalMapper.partialUpdate(existingSucursal, sucursalDTO);

                return existingSucursal;
            })
            .map(sucursalRepository::save)
            .map(sucursalMapper::toDto);
    }

    /**
     * Get all the sucursals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SucursalDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Sucursals");
        return sucursalRepository.findAll(pageable).map(sucursalMapper::toDto);
    }

    /**
     * Get one sucursal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SucursalDTO> findOne(Long id) {
        LOG.debug("Request to get Sucursal : {}", id);
        return sucursalRepository.findById(id).map(sucursalMapper::toDto);
    }

    /**
     * Delete the sucursal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Sucursal : {}", id);
        sucursalRepository.deleteById(id);
    }
}
