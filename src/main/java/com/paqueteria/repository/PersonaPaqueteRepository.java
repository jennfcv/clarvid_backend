package com.paqueteria.repository;

import com.paqueteria.domain.PersonaPaquete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonaPaquete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaPaqueteRepository extends JpaRepository<PersonaPaquete, Long> {}
