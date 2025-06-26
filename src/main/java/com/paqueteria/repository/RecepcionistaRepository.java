package com.paqueteria.repository;

import com.paqueteria.domain.Recepcionista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recepcionista entity.
 */
@Repository
public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Long> {

    default Optional<Recepcionista> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Recepcionista> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Recepcionista> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select recepcionista from Recepcionista recepcionista left join fetch recepcionista.usuario left join fetch recepcionista.sucursal",
        countQuery = "select count(recepcionista) from Recepcionista recepcionista"
    )
    Page<Recepcionista> findAllWithToOneRelationships(Pageable pageable);

    @Query("select recepcionista from Recepcionista recepcionista left join fetch recepcionista.usuario left join fetch recepcionista.sucursal")
    List<Recepcionista> findAllWithToOneRelationships();

    @Query("select recepcionista from Recepcionista recepcionista left join fetch recepcionista.usuario left join fetch recepcionista.sucursal where recepcionista.id =:id")
    Optional<Recepcionista> findOneWithToOneRelationships(@Param("id") Long id);
}
