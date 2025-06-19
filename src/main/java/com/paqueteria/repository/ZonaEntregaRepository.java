package com.paqueteria.repository;

import com.paqueteria.domain.ZonaEntrega;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ZonaEntrega entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZonaEntregaRepository extends JpaRepository<ZonaEntrega, Long> {}
