package com.paqueteria.repository;

import com.paqueteria.domain.ConfirmacionEntrega;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConfirmacionEntrega entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfirmacionEntregaRepository extends JpaRepository<ConfirmacionEntrega, Long> {}
