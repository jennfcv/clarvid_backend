package com.paqueteria.repository;

import com.paqueteria.domain.Repartidor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Repartidor entity.
 */
@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {
    default Optional<Repartidor> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Repartidor> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Repartidor> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select repartidor from Repartidor repartidor left join fetch repartidor.usuario",
        countQuery = "select count(repartidor) from Repartidor repartidor"
    )
    Page<Repartidor> findAllWithToOneRelationships(Pageable pageable);

    @Query("select repartidor from Repartidor repartidor left join fetch repartidor.usuario")
    List<Repartidor> findAllWithToOneRelationships();

    @Query("select repartidor from Repartidor repartidor left join fetch repartidor.usuario where repartidor.id =:id")
    Optional<Repartidor> findOneWithToOneRelationships(@Param("id") Long id);
}
