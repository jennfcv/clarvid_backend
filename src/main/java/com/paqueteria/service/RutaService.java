package com.paqueteria.service;

import com.paqueteria.domain.Ruta;
import com.paqueteria.repository.RutaRepository;
import com.paqueteria.service.dto.RutaDTO;
import com.paqueteria.service.mapper.RutaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.Ruta}.
 */
@Service
@Transactional
public class RutaService {

    private static final Logger LOG = LoggerFactory.getLogger(RutaService.class);

    private final RutaRepository rutaRepository;

    private final RutaMapper rutaMapper;

    public RutaService(RutaRepository rutaRepository, RutaMapper rutaMapper) {
        this.rutaRepository = rutaRepository;
        this.rutaMapper = rutaMapper;
    }

    /**
     * Save a ruta.
     *
     * @param rutaDTO the entity to save.
     * @return the persisted entity.
     */
    public RutaDTO save(RutaDTO rutaDTO) {
        LOG.debug("Request to save Ruta : {}", rutaDTO);
        Ruta ruta = rutaMapper.toEntity(rutaDTO);
        ruta = rutaRepository.save(ruta);
        return rutaMapper.toDto(ruta);
    }

    /**
     * Update a ruta.
     *
     * @param rutaDTO the entity to save.
     * @return the persisted entity.
     */
    public RutaDTO update(RutaDTO rutaDTO) {
        LOG.debug("Request to update Ruta : {}", rutaDTO);
        Ruta ruta = rutaMapper.toEntity(rutaDTO);
        ruta = rutaRepository.save(ruta);
        return rutaMapper.toDto(ruta);
    }

    /**
     * Partially update a ruta.
     *
     * @param rutaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RutaDTO> partialUpdate(RutaDTO rutaDTO) {
        LOG.debug("Request to partially update Ruta : {}", rutaDTO);

        return rutaRepository
            .findById(rutaDTO.getId())
            .map(existingRuta -> {
                rutaMapper.partialUpdate(existingRuta, rutaDTO);

                return existingRuta;
            })
            .map(rutaRepository::save)
            .map(rutaMapper::toDto);
    }

    /**
     * Get all the rutas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RutaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Rutas");
        return rutaRepository.findAll(pageable).map(rutaMapper::toDto);
    }

    /**
     * Get one ruta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RutaDTO> findOne(Long id) {
        LOG.debug("Request to get Ruta : {}", id);
        return rutaRepository.findById(id).map(rutaMapper::toDto);
    }

    /**
     * Delete the ruta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Ruta : {}", id);
        rutaRepository.deleteById(id);
    }
}
