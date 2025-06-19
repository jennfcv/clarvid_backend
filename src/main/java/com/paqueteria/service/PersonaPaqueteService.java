package com.paqueteria.service;

import com.paqueteria.domain.PersonaPaquete;
import com.paqueteria.repository.PersonaPaqueteRepository;
import com.paqueteria.service.dto.PersonaPaqueteDTO;
import com.paqueteria.service.mapper.PersonaPaqueteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.PersonaPaquete}.
 */
@Service
@Transactional
public class PersonaPaqueteService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaPaqueteService.class);

    private final PersonaPaqueteRepository personaPaqueteRepository;

    private final PersonaPaqueteMapper personaPaqueteMapper;

    public PersonaPaqueteService(PersonaPaqueteRepository personaPaqueteRepository, PersonaPaqueteMapper personaPaqueteMapper) {
        this.personaPaqueteRepository = personaPaqueteRepository;
        this.personaPaqueteMapper = personaPaqueteMapper;
    }

    /**
     * Save a personaPaquete.
     *
     * @param personaPaqueteDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonaPaqueteDTO save(PersonaPaqueteDTO personaPaqueteDTO) {
        LOG.debug("Request to save PersonaPaquete : {}", personaPaqueteDTO);
        PersonaPaquete personaPaquete = personaPaqueteMapper.toEntity(personaPaqueteDTO);
        personaPaquete = personaPaqueteRepository.save(personaPaquete);
        return personaPaqueteMapper.toDto(personaPaquete);
    }

    /**
     * Update a personaPaquete.
     *
     * @param personaPaqueteDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonaPaqueteDTO update(PersonaPaqueteDTO personaPaqueteDTO) {
        LOG.debug("Request to update PersonaPaquete : {}", personaPaqueteDTO);
        PersonaPaquete personaPaquete = personaPaqueteMapper.toEntity(personaPaqueteDTO);
        personaPaquete = personaPaqueteRepository.save(personaPaquete);
        return personaPaqueteMapper.toDto(personaPaquete);
    }

    /**
     * Partially update a personaPaquete.
     *
     * @param personaPaqueteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonaPaqueteDTO> partialUpdate(PersonaPaqueteDTO personaPaqueteDTO) {
        LOG.debug("Request to partially update PersonaPaquete : {}", personaPaqueteDTO);

        return personaPaqueteRepository
            .findById(personaPaqueteDTO.getId())
            .map(existingPersonaPaquete -> {
                personaPaqueteMapper.partialUpdate(existingPersonaPaquete, personaPaqueteDTO);

                return existingPersonaPaquete;
            })
            .map(personaPaqueteRepository::save)
            .map(personaPaqueteMapper::toDto);
    }

    /**
     * Get all the personaPaquetes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonaPaqueteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PersonaPaquetes");
        return personaPaqueteRepository.findAll(pageable).map(personaPaqueteMapper::toDto);
    }

    /**
     * Get one personaPaquete by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonaPaqueteDTO> findOne(Long id) {
        LOG.debug("Request to get PersonaPaquete : {}", id);
        return personaPaqueteRepository.findById(id).map(personaPaqueteMapper::toDto);
    }

    /**
     * Delete the personaPaquete by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PersonaPaquete : {}", id);
        personaPaqueteRepository.deleteById(id);
    }
}
