package com.paqueteria.repository;

import com.paqueteria.domain.CalificacionEntrega;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CalificacionEntrega entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalificacionEntregaRepository extends JpaRepository<CalificacionEntrega, Long> {}
