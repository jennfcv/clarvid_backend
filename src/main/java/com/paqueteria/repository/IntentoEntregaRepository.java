package com.paqueteria.repository;

import com.paqueteria.domain.IntentoEntrega;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntentoEntrega entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntentoEntregaRepository extends JpaRepository<IntentoEntrega, Long> {}
