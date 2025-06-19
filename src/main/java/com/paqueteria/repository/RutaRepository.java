package com.paqueteria.repository;

import com.paqueteria.domain.Ruta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ruta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {}
