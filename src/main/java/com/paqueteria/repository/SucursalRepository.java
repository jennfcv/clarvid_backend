package com.paqueteria.repository;

import com.paqueteria.domain.Sucursal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sucursal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {}
