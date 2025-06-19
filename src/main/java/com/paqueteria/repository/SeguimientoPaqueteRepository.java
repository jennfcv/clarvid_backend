package com.paqueteria.repository;

import com.paqueteria.domain.SeguimientoPaquete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SeguimientoPaquete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeguimientoPaqueteRepository extends JpaRepository<SeguimientoPaquete, Long> {}
