package com.paqueteria.repository;

import com.paqueteria.domain.ReportePDF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportePDF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportePDFRepository extends JpaRepository<ReportePDF, Long> {}
