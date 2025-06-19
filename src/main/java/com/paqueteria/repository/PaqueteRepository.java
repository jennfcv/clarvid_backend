package com.paqueteria.repository;

import com.paqueteria.domain.Paquete;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Paquete entity.
 */
@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Long> {
    @Query("select paquete from Paquete paquete where paquete.recepcionista.login = ?#{authentication.name}")
    List<Paquete> findByRecepcionistaIsCurrentUser();

    @Query("select paquete from Paquete paquete where paquete.repartidor.login = ?#{authentication.name}")
    List<Paquete> findByRepartidorIsCurrentUser();

    default Optional<Paquete> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Paquete> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Paquete> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select paquete from Paquete paquete left join fetch paquete.recepcionista left join fetch paquete.repartidor",
        countQuery = "select count(paquete) from Paquete paquete"
    )
    Page<Paquete> findAllWithToOneRelationships(Pageable pageable);

    @Query("select paquete from Paquete paquete left join fetch paquete.recepcionista left join fetch paquete.repartidor")
    List<Paquete> findAllWithToOneRelationships();

    @Query(
        "select paquete from Paquete paquete left join fetch paquete.recepcionista left join fetch paquete.repartidor where paquete.id =:id"
    )
    Optional<Paquete> findOneWithToOneRelationships(@Param("id") Long id);
}
