package com.paqueteria.repository;

import com.paqueteria.domain.UbicacionRepartidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UbicacionRepartidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UbicacionRepartidorRepository extends JpaRepository<UbicacionRepartidor, Long> {}
