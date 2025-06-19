package com.paqueteria.service;

import com.paqueteria.domain.ReportePDF;
import com.paqueteria.repository.ReportePDFRepository;
import com.paqueteria.service.dto.ReportePDFDTO;
import com.paqueteria.service.mapper.ReportePDFMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.paqueteria.domain.ReportePDF}.
 */
@Service
@Transactional
public class ReportePDFService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportePDFService.class);

    private final ReportePDFRepository reportePDFRepository;

    private final ReportePDFMapper reportePDFMapper;

    public ReportePDFService(ReportePDFRepository reportePDFRepository, ReportePDFMapper reportePDFMapper) {
        this.reportePDFRepository = reportePDFRepository;
        this.reportePDFMapper = reportePDFMapper;
    }

    /**
     * Save a reportePDF.
     *
     * @param reportePDFDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportePDFDTO save(ReportePDFDTO reportePDFDTO) {
        LOG.debug("Request to save ReportePDF : {}", reportePDFDTO);
        ReportePDF reportePDF = reportePDFMapper.toEntity(reportePDFDTO);
        reportePDF = reportePDFRepository.save(reportePDF);
        return reportePDFMapper.toDto(reportePDF);
    }

    /**
     * Update a reportePDF.
     *
     * @param reportePDFDTO the entity to save.
     * @return the persisted entity.
     */
    public ReportePDFDTO update(ReportePDFDTO reportePDFDTO) {
        LOG.debug("Request to update ReportePDF : {}", reportePDFDTO);
        ReportePDF reportePDF = reportePDFMapper.toEntity(reportePDFDTO);
        reportePDF = reportePDFRepository.save(reportePDF);
        return reportePDFMapper.toDto(reportePDF);
    }

    /**
     * Partially update a reportePDF.
     *
     * @param reportePDFDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReportePDFDTO> partialUpdate(ReportePDFDTO reportePDFDTO) {
        LOG.debug("Request to partially update ReportePDF : {}", reportePDFDTO);

        return reportePDFRepository
            .findById(reportePDFDTO.getId())
            .map(existingReportePDF -> {
                reportePDFMapper.partialUpdate(existingReportePDF, reportePDFDTO);

                return existingReportePDF;
            })
            .map(reportePDFRepository::save)
            .map(reportePDFMapper::toDto);
    }

    /**
     * Get all the reportePDFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReportePDFDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ReportePDFS");
        return reportePDFRepository.findAll(pageable).map(reportePDFMapper::toDto);
    }

    /**
     * Get one reportePDF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReportePDFDTO> findOne(Long id) {
        LOG.debug("Request to get ReportePDF : {}", id);
        return reportePDFRepository.findById(id).map(reportePDFMapper::toDto);
    }

    /**
     * Delete the reportePDF by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ReportePDF : {}", id);
        reportePDFRepository.deleteById(id);
    }
}
